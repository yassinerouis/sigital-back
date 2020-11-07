package org.sid.sigital;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sid.sigital.dao.UserRepository;
import org.sid.sigital.entities.UserApp;
import org.sid.sigital.security.JWTAuthenticationFilter;
import org.sid.sigital.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.SneakyThrows;

@SpringBootTest
class SigitalAuthentificationApplicationTests {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	UserDetailsServiceImpl userDetailsService;
	
	@Test
	void contextLoads() {
	}
	
	//vérifier si les attributs nom ou prénom ne sont pas vides pour tous les enregistrements de la table User
	
	@Test
	
	void testNotNullFirstAndLastNameUser() {
		List<UserApp> users=userRepository.findAll();
		int find=0;
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getFirstName() == null || users.get(i).getLastName() == null) {
				find=1;
			}
		}
		assertEquals(0, find);
	}
	
	//	Vérifier mais ignorer le test : si tous les utilisateurs enregistrés ont un role

	@Test
	@Disabled
	
	void testNotNullRoleUser() {
		List<UserApp> users=userRepository.findAll();
		for(int i=0;i<users.size();i++) {
			assertNotNull(users.get(i).getRole());
		}
	}

}