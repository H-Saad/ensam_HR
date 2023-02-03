package com.hr.springboot.notification_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.notification_module.models.Notification;

public interface NotifRepo extends JpaRepository<Notification, Integer>{
	@Query(value = "SELECT * FROM notification WHERE to = ?", nativeQuery = true)
	List<Notification> findAllbyUserID(int id);
}