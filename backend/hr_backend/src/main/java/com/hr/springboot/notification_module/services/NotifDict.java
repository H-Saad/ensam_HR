package com.hr.springboot.notification_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.service_module.models.Document;
import com.hr.springboot.service_module.repositories.DocRepo;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.validation_module.models.Request;

@Service
public class NotifDict {
	
	@Autowired
	private DocRepo dr;
	
	@Autowired 
	private UserRepo ur;
	
	public String reqSaved(Document d) {
		return "Document sauvegarde " + d.getTitle();
	}
	
	public String reqCreated(Document d) {
		return "Demande cree avec succes: " + d.getTitle();
	}
	
	public String approve(Request r) {
		return "Vous avez approuve la requete numero: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle();
	}
	
	public String approvedBy(User u, Request r) {
		return "Votre requete numero: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle() + " a ete approuve par: " + u.getNom() + " " + u.getPrenom();
	}
	
	public String refuse(Request r) {
		return "Vous avez refuse la requete numero: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle();
	}
	
	public String refusedBy(User u, Request r) {
		return "Votre requete numero: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle() + " a ete refuse par: " + u.getNom() + " " + u.getPrenom();
	}
	
	public String pendingReq(Request r) {
		return "Vous avez une requete en attente numero: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle() + " de la part de: " + ur.findById(r.getUser_id()).get().getNom() +
				" " + ur.findById(r.getUser_id()).get().getPrenom();
	}
	
	public String complete(Request r) {
		return "Votre document: " + dr.findById(r.getDocument_id()).get().getTitle() + " est pret pour etre recupere";
	}
}
