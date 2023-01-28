package com.hr.springboot.userData_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	JwtUtil util;
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getUser")
	public User getuser(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
		User u = util.getUserfromToken(auth);
		u.setPassword("");
		return u;
	}
}
