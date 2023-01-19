package com.hr.springboot.userData_module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.userData_module.services.UserService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/test")
public class Test {
	
	@Autowired
	UserService us;
	
	@PostConstruct
	public void initRoleUser() {
		us.initRoleUser();
	}
	
	@PreAuthorize("hasRole(CONSTS.USER_ROLE)")
	@GetMapping("user")
	public String testUser() {
		return "Congrats ur a user harry";
	}
	
	@PreAuthorize("hasRole(CONSTS.L3_ROLE)")
	@GetMapping("layer3")
	public String testLayer3() {
		return "Congrats ur a layer3 harry";
	}
	
	@PreAuthorize("hasRole(CONSTS.L2_ROLE)")
	@GetMapping("layer2")
	public String testLayer2() {
		return "Congrats ur the blue";
	}
	
	@PreAuthorize("hasRole(CONSTS.L1_ROLE)")
	@GetMapping("layer1")
	public String testLayer1() {
		return "Congrats ur the director";
	}
	
	@PreAuthorize("hasRole(CONSTS.SU_ROLE)")
	@GetMapping("su")
	public String testSu() {
		return "Amenos";
	}
}
