package com.hr.springboot.userData_module.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.springboot.userData_module.models.Grade;
import com.hr.springboot.userData_module.services.GradeService;

@CrossOrigin
@RestController
@RequestMapping("grade")
public class GradeController {

	@Autowired
	private GradeService gs;
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getAll")
	public ResponseEntity<List<Grade>> getAll(){
		return ResponseEntity.status(200).body(gs.getGrade());
	}
	
	
}
