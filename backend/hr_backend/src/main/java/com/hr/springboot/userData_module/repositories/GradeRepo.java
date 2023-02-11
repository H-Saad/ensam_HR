package com.hr.springboot.userData_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.springboot.userData_module.models.Grade;

public interface GradeRepo extends JpaRepository<Grade,String>{

}
