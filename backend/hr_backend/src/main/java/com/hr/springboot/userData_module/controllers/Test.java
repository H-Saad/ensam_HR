package com.hr.springboot.userData_module.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.service_module.models.Document;
import com.hr.springboot.service_module.models.Var;
import com.hr.springboot.service_module.services.DocService;
import com.hr.springboot.userData_module.services.UserService;
import com.opencsv.exceptions.CsvValidationException;


@RestController
@RequestMapping("/test")
public class Test {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	DocService dr;
	
	/*@PostConstruct
	public void initTestcases() {
		us.initRoleUser();
		dr.initTestDocs();
	}*/
	
	
	@PreAuthorize("hasRole('User')")
	@PostMapping("madness2")
	public void madness2(@RequestBody HashMap<String,Object> req) {
		ArrayList<HashMap<String,String>> a = (ArrayList<HashMap<String, String>>) req.get("vars");
		for(HashMap<String,String> b : a) {
			System.out.println(b.get("varname"));
			System.out.println(b.get("value"));
		}
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("madness")
	public ArrayList<HashMap<String,String>> madness() throws CsvValidationException, IOException{
		return dr.getFillableVars(new Document());
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("user")
	public String testUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
		System.out.println(util.getUsernameFromToken(auth.substring(7)));
		return "Congrats ur a user harry";
	}
	
	@PreAuthorize("hasRole('layer3')")
	@GetMapping("layer3")
	public String testLayer3() {
		return "Congrats ur a layer3 harry";
	}
	
	@PreAuthorize("hasRole('layer2')")
	@GetMapping("layer2")
	public String testLayer2() {
		return "Congrats ur the blue";
	}
	
	@PreAuthorize("hasRole('layer1')")
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
