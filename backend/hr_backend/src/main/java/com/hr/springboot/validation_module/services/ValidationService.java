package com.hr.springboot.validation_module.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.service_module.models.Document;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.validation_module.models.Completed_request;
import com.hr.springboot.validation_module.models.Pending_request;
import com.hr.springboot.validation_module.models.Refused_request;
import com.hr.springboot.validation_module.repositories.CompletedRepo;
import com.hr.springboot.validation_module.repositories.PendingRepo;
import com.hr.springboot.validation_module.repositories.RefusedRepo;

@Service
public class ValidationService {
	
	@Autowired
	PendingRepo pr;
	
	@Autowired
	RefusedRepo rr;
	
	@Autowired
	CompletedRepo cr;
	
	
	public void createRequest(User u, Document d) {
		Pending_request p = new Pending_request();
		p.setUser_id(u.getId());
		p.setDocument_id(d.getId());
		p.setDatetime(LocalDateTime.now());
		pr.save(p);
	}
	
	public void validatel1(Pending_request p, User u) {
		p.setApproved_by_l1(true);
		p.setL1_id(u.getId());
		p.setL1_datetime(LocalDateTime.now());
		pr.save(p);
	}
	
	public void validatel2(Pending_request p, User u) {
		p.setApproved_by_l2(true);
		p.setL2_id(u.getId());
		p.setL2_datetime(LocalDateTime.now());
		pr.save(p);
	}
	
	public void validatel3(Pending_request p, User u) {
		p.setApproved_by_l3(true);
		p.setL3_id(u.getId());
		p.setL3_datetime(LocalDateTime.now());
		pr.save(p);
	}
	
	public void completeRequest(Pending_request p) {
		Completed_request c = new Completed_request(p);
		pr.delete(p);
		cr.save(c);
	}
	
	public void refuseRequest(Pending_request p, User u, String refusal_msg) {
		Refused_request rf = new Refused_request(p);
		rf.setRefused_by(u.getId());
		rf.setRefusal_cause(refusal_msg);
		rf.setRefusal_datetime(LocalDateTime.now());
		pr.delete(p);
		rr.save(rf);
	}
	
	public List<Pending_request> getUserPending(User u){
		return pr.findByUserID(u.getId());
	}
	public List<Pending_request> getL3Pending(){
		return pr.findL3();
	}
	public List<Pending_request> getL2Pending(){
		return pr.findL2();
	}
	public List<Pending_request> getL1Pending(){
		return pr.findL1();
	}
	
	public List<Refused_request> getUserRefused(User u){
		return rr.findAllbyUserID(u.getId());
	}
	
	public List<Completed_request> getUserCompleted(User u){
		return cr.findAllbyUserID(u.getId());
	}
}
