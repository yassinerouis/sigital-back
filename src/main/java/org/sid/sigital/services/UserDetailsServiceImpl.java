package org.sid.sigital.services;

import java.util.ArrayList;
import java.util.Collection;

import org.sid.sigital.dao.UserRepository;
import org.sid.sigital.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//Pour récuperer et vérifier à partir de la base de données les informations de l'utilisateur authentifié
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserApp user = userRepository.findByUsername(username);
		System.out.println(user.getFirstName());
		if(user==null) throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> auts=new ArrayList<GrantedAuthority>();
		auts.add(new SimpleGrantedAuthority(user.getRole().getName()));
		return new User(user.getUsername(), user.getPassword(), auts);
	}
}