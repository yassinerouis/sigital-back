package org.sid.sigital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		
		//J'utilise JWTAuthorization filter que j'ai créé
		
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		//Je definie des droits d'accès
		
		//Le login et le register ne nécessitent pas de s'authentifier
		
		http.authorizeRequests().antMatchers("/login","/saveUser").permitAll();
		
		//Les autres requettes nécessitent une authentification avec le role par defaut USER
		
	    http.authorizeRequests().antMatchers("/","/**").hasAuthority("USER");
		
		//Nous n'avons pas besoin de la session proposée par spring car nous travaillerons avec JWT
	    
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//Toutes les requettes nécessitent une authentification
		
		http.authorizeRequests().anyRequest().authenticated();
		
		//Nous utilisons le filtre d'authentification JWT que nous avons créé
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
				
	}
	
}
