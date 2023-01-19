package com.hr.springboot.userData_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.springboot.userData_module.models.Role;

public interface RoleRepo extends JpaRepository<Role,String>{

}
