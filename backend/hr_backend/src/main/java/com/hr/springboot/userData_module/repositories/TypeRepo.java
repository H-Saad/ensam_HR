package com.hr.springboot.userData_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.springboot.userData_module.models.Type_personnel;

public interface TypeRepo extends JpaRepository<Type_personnel, Integer>{

}
