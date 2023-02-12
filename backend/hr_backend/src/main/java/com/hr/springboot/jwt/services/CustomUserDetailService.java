package com.hr.springboot.jwt.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = ur.authFindByMail(username).get();
		return new MyUserDetails(u);
	}
}
