package com.hr.springboot.userData_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.userData_module.models.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query(value = "SELECT * FROM user WHERE email = ?", nativeQuery = true)
	Optional<User> findByMail(String mail);
	
	@Query(value = "select * from user u, user_role ur, role r where u.id = ur.user_id and ur.role_id = r.role_name and r.role_name = ?", nativeQuery = true)
	Optional<User> findByRole(String role);
}
