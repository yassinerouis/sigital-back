package org.sid.sigital;

import java.util.Date;
import org.sid.sigital.dao.RoleRepository;
import org.sid.sigital.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

@SpringBootApplication
public class SigitalAuthentificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigitalAuthentificationApplication.class, args);
	}
	
	//Ajouter un role dans la base de données pour tester
	
	@Bean
	CommandLineRunner start(RoleRepository roleRepository) {
		return args->{
			Role role=new Role(1L,"USER");
			roleRepository.save(role);
		};
	}
}
