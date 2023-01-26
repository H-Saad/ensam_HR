package com.hr.springboot.validation_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.validation_module.models.Refused_request;

public interface RefusedRepo extends JpaRepository<Refused_request, Integer>{
	@Query(value = "select * from refused_request where user_id = ?", nativeQuery = true)
	List<Refused_request> findAllbyUserID(int id);
}
