package com.hr.springboot.service_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.service_module.models.Document;

public interface DocRepo extends JpaRepository<Document, Integer>{
	@Query(value = "select * from document d, doc_role dr, role r where d.id = dr.doc_id and dr.role_id = r.role_name and r.role_name = ?", nativeQuery = true)
	List<Document> findByRole(String role);
	
	@Query(value = "select * from document d, doc_role dr, role r, doc_pers dp, type_personnel p where d.id = dr.doc_id and d.id = dp.doc_id and dr.role_id = r.role_name and dp.pers_id = p.id and r.role_name = ? and p.id = ? ", nativeQuery = true)
	List<Document> findByRolePers(String role, String pers);
}
