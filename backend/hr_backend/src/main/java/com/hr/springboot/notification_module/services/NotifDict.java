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
		return "Document sauvegardé " + d.getTitle();
	}
	
	public String reqCreated(Document d) {
		return "Demande cree avec succès: " + d.getTitle();
	}
	
	public String approve(Request r) {
		return "Vous avez approuvé la requête numéro: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle();
	}
	
	public String approvedBy(User u, Request r) {
		return "Votre requête numéro: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle() + " a été approuvé par: " + u.getNom() + " " + u.getPrenom();
	}
	
	public String refuse(Request r) {
		return "Vous avez refusé la requête numéro: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle();
	}
	
	public String refusedBy(User u, Request r) {
		return "Votre requête numéro: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle() + " a été refusé par: " + u.getNom() + " " + u.getPrenom();
	}
	
	public String pendingReq(Request r) {
		return "Vous avez une requête en attente numéro: " + r.getId() + " : " + dr.findById(r.getDocument_id()).get().getTitle() + " de la part de: " + ur.findById(r.getUser_id()).get().getNom() +
				" " + ur.findById(r.getUser_id()).get().getPrenom();
	}
	
	public String complete(Request r) {
		return "Votre document: " + dr.findById(r.getDocument_id()).get().getTitle() + " est prêt pour être récupéré";
	}
}
