package org.sid.sigital.controllers;

import org.sid.sigital.dao.RoleRepository;
import org.sid.sigital.dao.UserRepository;
import org.sid.sigital.entities.Role;
import org.sid.sigital.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	//pour sauvegarder l'utilisateur à partir des informations saisies avec le Role par défaut (USER)
	
	@PostMapping("users/save")
	public UserApp saveUser(@RequestBody UserApp user) {
		Role role=roleRepository.getOne(1L);
		user.setRole(role);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	//Pour récuperer un utilisateur à partir de la base de données
	
	@GetMapping("users/get/{username}")
	public UserApp saveUser(@PathVariable("username") String username) {
		return userRepository.findByUsername(username);
	}
	
}