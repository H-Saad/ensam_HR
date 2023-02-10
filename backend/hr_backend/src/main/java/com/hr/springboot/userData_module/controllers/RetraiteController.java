package com.hr.springboot.userData_module.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.springboot.userData_module.services.RetraiteService;
import com.hr.springboot.userData_module.models.Retraite;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;

@RestController
@RequestMapping("retraite")
public class RetraiteController {
	
	@Autowired
	private RetraiteService rs;

	@Autowired
	private UserRepo ur;
	
	@GetMapping("getAll")
	public ResponseEntity<List<Retraite>> getAll(){
		return ResponseEntity.status(200).body(rs.getRetraites());
	}
	
	
	@GetMapping("markDone/{email}")
	public ResponseEntity<HashMap<String,String>> markDone(@PathVariable String email){
		User u = ur.findByMail(email).get();
		rs.markAsDone(u);
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("status", "success");
		return ResponseEntity.status(200).body(ret);
	}
}
