package com.hr.springboot.userData_module.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class Role {
	@Id
	@Column(name="roleName")
	private String roleName;
	@Column(name="description")
	private String description;
	
	public Role() {
		
	}
	
	public Role(String roleName, String description) {
		super();
		this.roleName = roleName;
		this.description = description;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDesc() {
		return description;
	}

	public void setDesc(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [roleName=" + roleName + ", description=" + description + "]";
	}

	
	
}