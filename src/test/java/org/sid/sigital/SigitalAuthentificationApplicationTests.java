package org.sid.sigital;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sid.sigital.dao.UserRepository;
import org.sid.sigital.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SigitalAuthentificationApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Test
	void contextLoads() {
	}
	
	//vérifier si les attributs nom ou prénom ne sont pas vides pour tous les enregistrements de la table User
	
	@Test
	
	void testNotNullFirstAndLastNameUser() {
		List<UserApp> users=userRepository.findAll();
		int find=0;
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getFirstName()==null ||users.get(i).getLastName()==null) {
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