package com.hr.springboot.config;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hr.springboot.notification_module.services.NotifDict;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.userData_module.models.Grade;
import com.hr.springboot.userData_module.models.Retraite;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.GradeRepo;
import com.hr.springboot.userData_module.repositories.RetraiteRepo;
import com.hr.springboot.userData_module.repositories.UserRepo;

@Component
public class ScheduledTasks {
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private RetraiteRepo rr;
	
	@Autowired
	private NotifService ns;
	
	@Autowired
	private NotifDict nd;
	
	@Autowired
	private GradeRepo gr;
	
	@Scheduled(initialDelay = 100, fixedDelay = 2678400000L)
	public void updateRetraite() {
		System.out.println("Executed first time retraite");
		List<User> l = ur.findAllNonRet();
		User rh = ur.findByRole("layer3").get();
		for(User u:l) {
			String type = "";
			Set<Type_personnel> s = u.getType_personnel();
			for(Type_personnel p:s) {
				type = p.getId();
				break;
			}
			long months = 0;
			try {
				LocalDate birth = u.getDate_naissance();
				LocalDate now = LocalDate.now();
				Period diff = Period.between(birth, now);
				months = diff.toTotalMonths();
			}catch(NullPointerException e) {
				System.out.println("Skipped: "+u.getEmail());
				continue;
			}
			switch(type) {
				case "Administratif":
					if(months>=735) {
						ns.makeNotif(rh, rh, null, nd.newRetraite());
						Retraite temp = new Retraite();
						temp.setEmail(u.getEmail());
						temp.setNom(u.getNom());
						temp.setPrenom(u.getPrenom());
						temp.setGenre(u.getGenre());
						temp.setCin(u.getCin());
						temp.setDate_naissance(u.getDate_naissance());
						temp.setLieu_naissance(u.getLieu_naissance());
						temp.setDate_recrutement(u.getDate_recrutement());
						temp.setNum_tel(u.getNum_tel());
						temp.setType_pers(type);
						rr.save(temp);
					}
					
					break;
				case "Enseignant":
					if(months>=765) {
						ns.makeNotif(rh, rh, null, nd.newRetraite());
						Retraite temp = new Retraite();
						temp.setEmail(u.getEmail());
						temp.setNom(u.getNom());
						temp.setPrenom(u.getPrenom());
						temp.setGenre(u.getGenre());
						temp.setCin(u.getCin());
						temp.setDate_naissance(u.getDate_naissance());
						temp.setLieu_naissance(u.getLieu_naissance());
						temp.setDate_recrutement(u.getDate_recrutement());
						temp.setNum_tel(u.getNum_tel());
						temp.setType_pers(type);
						rr.save(temp);
					}
					break;
			}
		}
	}
	
	@Scheduled(initialDelay = 100, fixedDelay = 2678400000L)
	public void updateGrade() {
		System.out.println("executed first time grade");
		List<User> l = ur.findAll();
		User rh = ur.findByRole("layer3").get();
		for(User u:l) {
			long months = 0;
			try {
				LocalDate effet = u.getDate_effet_grade();
				LocalDate now = LocalDate.now();
				Period diff = Period.between(effet, now);
				months = diff.toTotalMonths();
				System.out.println(u.getEmail());
				System.out.println(months);
			}catch(NullPointerException e) {
				System.out.println("Skipped: "+u.getEmail());
				continue;
			}
			Grade temp = new Grade();
			String type = "";
			Set<Type_personnel> s = u.getType_personnel();
			for(Type_personnel p:s) {
				type = p.getId();
				break;
			}
			temp.setEmail(u.getEmail());
			temp.setNom(u.getNom());
			temp.setPrenom(u.getPrenom());
			temp.setGenre(u.getGenre());
			temp.setCin(u.getCin());
			temp.setDate_naissance(u.getDate_naissance());
			temp.setLieu_naissance(u.getLieu_naissance());
			temp.setDate_recrutement(u.getDate_recrutement());
			temp.setNum_tel(u.getNum_tel());
			temp.setType_pers(type);
			temp.setDate_effet_grade(u.getDate_effet_grade());
			temp.setCode_grade(u.getCode_grade());
			temp.setGrade(u.getGrade());
			temp.setEchelon(u.getEchelon());
			temp.setDate_effet_echelon(u.getDate_effet_echelon());
			if(months >= 96) {
				temp.setExceptionel(true);
				ns.makeNotif(u, rh, null, nd.newExceptionel(u));
				gr.save(temp);
			}
			if(months >= 108) {
				temp.setRapide(true);
				ns.makeNotif(u, rh, null, nd.newRapide(u));
				gr.save(temp);
			}
			if(months >= 120) {
				temp.setNormal(true);
				ns.makeNotif(u, rh, null, nd.newNormal(u));
				gr.save(temp);
			}
		}
	}
}
