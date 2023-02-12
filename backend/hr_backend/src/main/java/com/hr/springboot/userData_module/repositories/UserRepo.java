package com.hr.springboot.userData_module.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.userData_module.models.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query(value = "SELECT * FROM user WHERE email = ? AND disabled=false", nativeQuery = true)
	Optional<User> findByMail(String mail);
	
	@Query(value = "select * from user u, user_role ur, role r where u.id = ur.user_id and ur.role_id = r.role_name and r.role_name = ? AND u.disabled=false", nativeQuery = true)
	Optional<User> findByRole(String role);
	
	@Query(value = "SELECT * FROM user WHERE disabled=true", nativeQuery = true)
	List<User> findAllDisabled();
	
	@Query(value = "select * from user u, type_personnel t, user_type ut where u.id = ut.user_id and t.id = ut.type_id and ut.type_id = 'Administratif'", nativeQuery = true)
	List<User> findAllAdministratif();
	
	@Query(value = "select * from user u, type_personnel t, user_type ut where u.id = ut.user_id and t.id = ut.type_id and ut.type_id = 'Enseignant'", nativeQuery = true)
	List<User> findAllEnseignant();
	
	@Query(value = "select * from user where retraite_flag = false", nativeQuery = true)
	List<User> findAllNonRet();
	
	@Query(value = "SELECT * FROM user WHERE email = ?", nativeQuery = true)
	Optional<User> authFindByMail(String mail);
}
