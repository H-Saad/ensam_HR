package com.hr.springboot.notification_module.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.notification_module.models.Notification;
import com.hr.springboot.notification_module.repositories.NotifRepo;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.validation_module.models.Request;

@Service
public class NotifService {

	@Autowired
	private NotifRepo nr;
	
	public void makeNotif(User from, User to, Request r, String desc) {
		Notification n = new Notification();
		n.setFrom(from.getId());
		n.setTo(to.getId());
		if(r != null) {
			n.setRequest_id(r.getId());
		}
		n.setDescription(desc);
		n.setRead(false);
		if(r != null) {
			n.setRequest_type(r.getType());
		}		
		
		nr.save(n);
	}
	
	public Notification getNotifByID(int id) {
		return nr.findById(id).get();
	}
	
	public List<Notification> getAllByUserID(User u){
		return nr.findAllbyUserID(u.getId());
	}
	
	public void markRead(int notif_id) {
		Notification n = getNotifByID(notif_id);
		n.setRead(true);
		nr.save(n);
	}
	
	public void markAllAsRead(User u) {
		List<Notification> l = nr.findAllbyUserID(u.getId());
		for(Notification n : l) {
			n.setRead(true);
		}
		nr.saveAll(l);
	}
	
	public void deleteSingle(int notif_id) {
		Notification n = getNotifByID(notif_id);
		nr.delete(n);
	}
	
	public void deleteAllByUser(User u) {
		List<Notification> l = nr.findAllbyUserID(u.getId());
		nr.deleteAll(l);
	}
	
}
