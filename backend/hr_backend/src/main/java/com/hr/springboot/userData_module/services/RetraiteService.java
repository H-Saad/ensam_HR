package com.hr.springboot.userData_module.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.notification_module.services.NotifDict;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.userData_module.models.Retraite;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.RetraiteRepo;
import com.hr.springboot.userData_module.repositories.UserRepo;

@Service
public class RetraiteService {
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private RetraiteRepo rr;
	
	@Autowired
	private NotifService ns;
	
	@Autowired
	private NotifDict nd;
	
	
	public void markAsDone(User u) {
		u.setRetraite_flag(true);
		ur.save(u);
		Retraite temp = rr.findById(u.getEmail()).get();
		rr.delete(temp);
	}
	
	public List<Retraite> getRetraites(){
		return rr.findAll();
	}
}
