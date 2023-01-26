package com.hr.springboot.validation_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.validation_module.models.Pending_request;

public interface PendingRepo extends JpaRepository<Pending_request, Integer>{
	@Query(value = "select * from pending_request where approved_by_l3 = 0", nativeQuery = true)
	List<Pending_request> findL3();

	@Query(value = "select * from pending_request where approved_by_l3 = 1 AND approved_by_l2 = 0", nativeQuery = true)
	List<Pending_request> findL2();

	@Query(value = "select * from pending_request where approved_by_l3 = 1 AND approved_by_l2 = 1 AND approved_by_l1 = 0", nativeQuery = true)
	List<Pending_request> findL1();

	@Query(value = "select * from pending_request where user_id = ?", nativeQuery = true)
	List<Pending_request> findByUserID(int user_id);
}
