package com.hr.springboot.validation_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.validation_module.models.Completed_request;

public interface CompletedRepo extends JpaRepository<Completed_request, Integer>{
	@Query(value = "select * from completed_request where user_id = ?", nativeQuery = true)
	List<Completed_request> findAllbyUserID(int id);
}
