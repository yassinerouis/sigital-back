package org.sid.sigital.dao;

import org.sid.sigital.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserApp, Long>{
	
	//Récuperer l'utilisateur qui a le username=username
	
	@Query("FROM UserApp where username=?1")
	public UserApp findByUsername(String username);
	
}
