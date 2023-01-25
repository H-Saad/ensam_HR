package com.hr.springboot.userData_module.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.userData_module.models.Role;
import com.hr.springboot.userData_module.repositories.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private RoleRepo rr;
	
	public Role createNewRole(Role role) {
		return rr.save(role);
	}
	
	public List<Role> getRoles(){
		return rr.findAll();
	}
	
	public Role getRole(String role) {
		return rr.findById(role).get();
	}
}