package com.hr.springboot.userData_module.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.userData_module.services.UserService;


@RestController
@RequestMapping("/test")
public class Test {
	
	@Autowired
	UserService us;
	
	/*@PostConstruct
	public void initRoleUser() {
		us.initRoleUser();
	}*/
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("user")
	public String testUser() {
		return "Congrats ur a user harry";
	}
	
	@PreAuthorize("hasRole('layer3_Admin')")
	@GetMapping("layer3")
	public String testLayer3() {
		return "Congrats ur a layer3 harry";
	}
	
	@PreAuthorize("hasRole('layer2_Admin')")
	@GetMapping("layer2")
	public String testLayer2() {
		return "Congrats ur the blue";
	}
	
	@PreAuthorize("hasRole('layer1_Admin')")
	@GetMapping("layer1")
	public String testLayer1() {
		return "Congrats ur the director";
	}
	
	@PreAuthorize("hasRole('superuser')")
	@GetMapping("su")
	public String testSu() {
		return "Amenos";
	}
}
