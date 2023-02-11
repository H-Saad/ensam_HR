package com.hr.springboot.userData_module.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.userData_module.models.Grade;
import com.hr.springboot.userData_module.repositories.GradeRepo;

@Service
public class GradeService {
	
	@Autowired
	private GradeRepo gr;
	
	public List<Grade> getGrade(){
		return gr.findAll();
	}
	
}
