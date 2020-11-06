package org.sid.sigital.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

	//Pour chaque requette recu au niveau du serveur et qui veut demander un traitement à notre API cette méthode s'exécute
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Demander au serveur la permission d'envoyer des requêtes à partir d'un autre domaine
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		//Les en-têtes qui sont autorisés à figurer dans une demande envoyée par le client
		
		response.addHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method,"
						+ "Access-Control-Request-Headers, authorization");
		
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		
		//Les en-têtes qui sont autorisés à être exposés dans la requête envoyée par le serveur
		
		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");

		//Ne pas activer la sécurité si la méthode de demande est OPTIONS
		
		if(request.getMethod().equals("OPTIONS")) response.setStatus(HttpServletResponse.SC_OK);
		else {
			
		//Récuperer le Token JWT 
			
		String jwt = request.getHeader(SecurityConstants.HEADER);
		System.out.println(jwt);

		if(jwt==null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response); return;
		}
				
		Claims claims = Jwts.parser()
						.setSigningKey(SecurityConstants.SECRET)
						.parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
						.getBody();
		String username = claims.getSubject();
		System.out.println("22222");

		//Récuperer les roles de l'utilisateur qui à envoyer la requette 
		
		ArrayList<Map<String, String>> roles =  (ArrayList<Map<String, String>>) claims.get("roles");
		Collection<GrantedAuthority> authoroties = new ArrayList<GrantedAuthority>();
		roles.forEach(r-> 
				{
					System.out.println("22222"+r.get("authority"));
					authoroties.add(new SimpleGrantedAuthority(r.get("authority")));
			});
		
		//Charger l'utilisateur authentifié
		
		UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(username, null, authoroties);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		filterChain.doFilter(request, response);
	}
		
	}

}
