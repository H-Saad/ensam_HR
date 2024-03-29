package com.hr.springboot.userData_module.controllers;

import java.util.HashMap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.springboot.userData_module.services.RetraiteService;
import com.hr.springboot.userData_module.models.Retraite;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("retraite")
public class RetraiteController {
	
	@Autowired
	private RetraiteService rs;

	@Autowired
	private UserRepo ur;
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getAll")
	public ResponseEntity<List<Retraite>> getAll(){
		return ResponseEntity.status(200).body(rs.getRetraites());
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("markDone/{email}")
	public ResponseEntity<HashMap<String,String>> markDone(@PathVariable String email){
		User u = ur.findByMail(email).get();
		rs.markAsDone(u);
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("status", "success");
		return ResponseEntity.status(200).body(ret);
	}
}
