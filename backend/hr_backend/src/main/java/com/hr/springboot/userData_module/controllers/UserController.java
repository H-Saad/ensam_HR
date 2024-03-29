package com.hr.springboot.userData_module.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.userData_module.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil util;
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getUser")
	public User getuser(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
		User u = util.getUserfromToken(auth);
		u.setPassword("");
		return u;
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getUserbyID/{id}")
	public User getuserbyid(@PathVariable int id) {
		User u = ur.findById(id).get();
		u.setPassword("");
		return u;
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("arabic")
	public ResponseEntity<String> arabic(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		if(u.getAR_nom()==null || u.getAR_prenom()==null) {
			return ResponseEntity.status(401).body("no");
		}
		return ResponseEntity.status(200).body("ok");
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("putarabic")
	public ResponseEntity<String> putarabic(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		u.setAR_nom(req.get("nom"));
		u.setAR_prenom(req.get("prenom"));
		ur.save(u);
		return ResponseEntity.status(200).body("yes");
	}
	
	/*
	 * request json:
	 * {
	 * 		"old":old,
	 * 		"new":new
	 * }
	 */
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("chpass")
	public ResponseEntity<HashMap<String,String>> chpass(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		HashMap<String,String> resp = new HashMap<String,String>();
		System.out.println(req.get("old"));
		System.out.println(req.get("new"));
		System.out.println(passwordEncoder.matches(req.get("old"), u.getPassword()));
		if(passwordEncoder.matches(req.get("old"), u.getPassword())) {
			u.setPassword(passwordEncoder.encode(req.get("new")));
			ur.save(u);
			resp.put("status", "ok");
			return ResponseEntity.status(200).body(resp);
		}
		resp.put("status", "wrong password");
		return ResponseEntity.status(400).body(resp);
	}
}
