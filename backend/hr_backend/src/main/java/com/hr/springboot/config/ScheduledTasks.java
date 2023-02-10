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
import com.hr.springboot.userData_module.models.Retraite;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
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
	
	@Scheduled(initialDelay = 100, fixedDelay = 2678400000L)
	public void updateRetraite() {
		System.out.println("Executed first time");
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
}
