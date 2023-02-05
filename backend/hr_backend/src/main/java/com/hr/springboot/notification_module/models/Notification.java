package com.hr.springboot.notification_module.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int from_id;
	private int to_id;
	private int request_id;
	private String description;
	private LocalDateTime timestamp;
	private boolean read_state;
	private String request_type;
	
	public Notification() {
		
	}

	public Notification(int id, int from_id, int to_id, int request_id, String description, LocalDateTime timestamp,
			boolean read_state, String request_type) {
		super();
		this.id = id;
		this.from_id = from_id;
		this.to_id = to_id;
		this.request_id = request_id;
		this.description = description;
		this.timestamp = timestamp;
		this.read_state = read_state;
		this.request_type = request_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFrom() {
		return from_id;
	}

	public void setFrom(int from) {
		this.from_id = from;
	}

	public int getTo() {
		return to_id;
	}

	public void setTo(int to) {
		this.to_id = to;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isRead() {
		return read_state;
	}

	public void setRead(boolean read) {
		this.read_state = read;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	
}
