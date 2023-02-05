package com.hr.springboot.userData_module.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Type_personnel")
public class Type_personnel {
	@Id
	private String id;
	
	public Type_personnel() {
		
	}

	public Type_personnel(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
