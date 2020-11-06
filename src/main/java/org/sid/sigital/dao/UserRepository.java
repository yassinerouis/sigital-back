package org.sid.sigital.dao;

import org.sid.sigital.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApp, Long>{

}
