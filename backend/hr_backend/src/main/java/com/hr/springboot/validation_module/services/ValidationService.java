package com.hr.springboot.validation_module.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.mailing_module.services.MailDict;
import com.hr.springboot.mailing_module.services.MailService;
import com.hr.springboot.notification_module.services.NotifDict;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.service_module.models.Document;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.validation_module.models.Completed_request;
import com.hr.springboot.validation_module.models.Pending_request;
import com.hr.springboot.validation_module.models.Refused_request;
import com.hr.springboot.validation_module.models.Request;
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
	
	@Autowired 
	NotifService ns;
	
	@Autowired
	NotifDict nd;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	MailService ms;
	
	@Autowired
	MailDict md;
	
	
	public Request createRequest(User u, Document d, String filename) {
		Pending_request p = new Pending_request();
		p.setUser_id(u.getId());
		p.setDocument_id(d.getId());
		p.setDatetime(LocalDateTime.now());
		p.setUser_file(filename);
		p.setDoc_title(d.getTitle());
		p.setCurrentfile(filename);
		pr.save(p);
		
		ms.sendmail(u, md.requestCreated(u, p));
		ms.sendmail(ur.findByRole("layer3").get(), md.requestAwaiting(ur.findByRole("layer3").get(), p), p.getUser_file());
		ms.sendmail(ur.findByRole("layer2").get(), md.requestAwaiting(ur.findByRole("layer2").get(), p), p.getUser_file());
		
		return p;
	}
	
	public Request createRequest(User u, Document d, String filename, String coTitle) {
		Pending_request p = new Pending_request();
		p.setUser_id(u.getId());
		p.setDocument_id(d.getId());
		p.setDatetime(LocalDateTime.now());
		p.setUser_file(filename);
		p.setDoc_title(d.getTitle() +": "+coTitle);
		p.setCurrentfile(filename);
		pr.save(p);
		
		ms.sendmail(u, md.requestCreated(u, p));
		ms.sendmail(ur.findByRole("layer3").get(), md.requestAwaiting(ur.findByRole("layer3").get(), p), p.getUser_file());
		ms.sendmail(ur.findByRole("layer2").get(), md.requestAwaiting(ur.findByRole("layer2").get(), p), p.getUser_file());
		
		return p;
	}
	
	
	public void validatel1(Pending_request p, User u, String filename) {
		p.setApproved_by_l1(true);
		p.setL1_id(u.getId());
		p.setL1_datetime(LocalDateTime.now());
		p.setL1_file(filename);
		if(filename!=null) {
			if(!filename.isEmpty()) {
				p.setCurrentfile(filename);
			}
		}
		pr.save(p);
	}
	
	public void validatel2(Pending_request p, User u, String filename) {
		p.setApproved_by_l2(true);
		p.setL2_id(u.getId());
		p.setL2_datetime(LocalDateTime.now());
		p.setL2_file(filename);
		if(filename!=null) {
			if(!filename.isEmpty()) {
				p.setCurrentfile(filename);
			}
		}
		pr.save(p);
		
		ms.sendmail(ur.findByRole("layer1").get(), md.requestAwaiting(ur.findByRole("layer3").get(), p), p.getUser_file());
	}
	
	public void validatel3(Pending_request p, User u, String filename) {
		p.setApproved_by_l3(true);
		p.setL3_id(u.getId());
		p.setL3_datetime(LocalDateTime.now());
		p.setL3_file(filename);
		if(filename!=null) {
			if(!filename.isEmpty()) {
				p.setCurrentfile(filename);
			}
		}
		pr.save(p);
		
		ms.sendmail(ur.findByRole("layer1").get(), md.requestAwaiting(ur.findByRole("layer3").get(), p), p.getUser_file());
		
		//notifier l'utilisateur que sa requete est approuve
		//ns.makeNotif(u, ur.findById(p.getUser_id()).get(), p, nd.approvedBy(u, p));
	}
	
	public void completeRequest(Pending_request p) {
		Completed_request c = new Completed_request(p);
		pr.delete(p);
		if(p.getL1_datetime()!=null) {
			c.setCompletion_date(p.getL1_datetime());
		}else {
			if(p.getL2_datetime()!=null) {
				c.setCompletion_date(p.getL2_datetime());
			}else {
				if(p.getL3_datetime()!=null) {
					c.setCompletion_date(p.getL3_datetime());
				}
				else {
					c.setCompletion_date(p.getDatetime());
				}
			}
		}
		cr.save(c);
		ns.makeNotif(ur.findById(p.getUser_id()).get(), ur.findById(p.getUser_id()).get(), c, nd.complete(c));
		ms.sendmail(ur.findById(c.getUser_id()).get(), md.docRdy(ur.findById(c.getUser_id()).get(), c), c.getFinal_file());
	}
	
	public void refuseRequest(Pending_request p, User u, String refusal_msg) {
		Refused_request rf = new Refused_request(p);
		rf.setRefused_by(u.getId());
		rf.setRefusal_cause(refusal_msg);
		rf.setRefusal_datetime(LocalDateTime.now());
		pr.delete(p);
		rr.save(rf);
		//notifier l utilisateur que sa requete est refuse
		ns.makeNotif(u, ur.findById(p.getUser_id()).get(), rf, nd.refusedBy(u, p));
		ms.sendmail((ur.findById(rf.getUser_id()).get()), md.requestRefused(ur.findById(rf.getUser_id()).get(), rf));
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
