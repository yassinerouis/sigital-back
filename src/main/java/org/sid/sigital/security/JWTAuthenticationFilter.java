package org.sid.sigital.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sid.sigital.entities.UserApp;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	public JWTAuthenticationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		// Pour récuperer les données de l'utilisateur lors de l'authentification
		
		UserApp user = null;	
		
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), UserApp.class);
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}
	
	// Lorsque l'authentification passe avec succès
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User springuser = (User) authResult.getPrincipal();
		
		//Génerer le token JWT
		
		String jwt = Jwts.builder()
					.setSubject(springuser.getUsername())
					.setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
					.claim("roles", springuser.getAuthorities())
					.compact();
		
		//Ajouter le token à la réponse token to the response
		
		response.addHeader(SecurityConstants.HEADER, SecurityConstants.TOKEN_PREFIX+jwt);
	}

}
