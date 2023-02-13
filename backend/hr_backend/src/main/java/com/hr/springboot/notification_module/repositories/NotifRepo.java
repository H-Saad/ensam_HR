package com.hr.springboot.notification_module.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hr.springboot.notification_module.models.Notification;

public interface NotifRepo extends JpaRepository<Notification, Integer>{
	@Query(value = "SELECT * FROM notification WHERE to_id = ? order by read_state ASC, timestamp DESC", nativeQuery = true)
	List<Notification> findAllbyUserID(int id);
}
