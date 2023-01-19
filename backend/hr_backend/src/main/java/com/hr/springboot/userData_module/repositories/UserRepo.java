package com.hr.springboot.userData_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.userData_module.models.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query(value = "SELECT * FROM user WHERE email = ?", nativeQuery = true)
	Optional<User> findByMail(String mail);
}
