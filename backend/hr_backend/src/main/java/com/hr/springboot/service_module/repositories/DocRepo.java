package com.hr.springboot.service_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.service_module.models.Document;

public interface DocRepo extends JpaRepository<Document, Integer>{
	@Query(value = "select * from document d, doc_role dr, role r where d.id = dr.doc_id and dr.role_id = r.role_name and r.role_name = ?", nativeQuery = true)
	List<Document> findByRole(String role);
}
