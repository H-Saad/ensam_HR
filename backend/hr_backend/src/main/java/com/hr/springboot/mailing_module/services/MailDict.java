package com.hr.springboot.mailing_module.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.service_module.services.DocService;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.validation_module.models.Refused_request;
import com.hr.springboot.validation_module.models.Request;

@Service
public class MailDict {
	
	@Autowired
	private DocService ds;
	
	@Autowired
	private UserRepo ur;

	public HashMap<String,String> accCreated(User u, String password) {
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Confirmation de création de compte");
		ret.put("body", "Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+",\n\n\n"
				+ "Nous sommes ravis de vous informer que votre compte a été créé avec succès sur notre application."
				+ "\n\n\n"
				+ "Vous pouvez maintenant vous connecter à tout moment en utilisant les informations d'identification suivants:"
				+ "Email: "+u.getEmail()+"\n"
				+ "Mot de passe: "+password+"\n\n\n"
				+ "Cordialement."); 
		return ret;
	}
	
	public HashMap<String,String> requestCreated(User u, Request r){
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Confirmation de création de demande: "+r.getDoc_title());
		ret.put("body","Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+",\n\n\n"
				+"Votre demande: "+r.getDoc_title()+" a été créé avec succès.\n"
				+"Vous pouvez suivre l'état de votre requête en consultant la rubrique 'Demandes'.\n\n\n"
				+"Cordialement.");
		return ret;
	}
	
	public HashMap<String,String> docRdy(User u, Request r){
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Document: "+r.getDoc_title()+"prêt.");
		ret.put("body","Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+",\n\n\n"
				+"Nous sommes ravis de vous informer que votre document "+r.getDoc_title()+" a été approuvé et signé avec succès.\n"
				+"Il est maintenant disponible au sein de la direction pour récupération.\n\n\n"
				+"Cordialement.");
		return ret;
	}
	
	public HashMap<String,String> requestRefused(User u, Refused_request r){
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Refus de la demande: "+r.getDoc_title());
		ret.put("body","Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+"\n\n\n"
				+"Nous avons le regret de vous informer que votre demande de: "+r.getDoc_title()+" a été refusé .\n\n\n"
				+"Motif: "+r.getRefusal_cause()+"\n\n\n"
				+"Cordialement.");
		return ret;
	}
	
	public HashMap<String,String> accLocked(User u){
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Blocage de votre compte d'utilisateur");
		ret.put("body", "Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+",\n\n\n"
				+ "Nous regrettons de vous informer que votre compte d'utilisateur a été bloqué après trois tentatives de connexion infructueuses.\n\n"
				+ "Cela a été fait dans le but de protéger votre compte et vos informations personnelles contre toute activité suspecte.\n\n"
				+ "Veuillez clicker sur le lien 'mot de passe oublié' dans la page de login ou vous rendre à l'administration pour plus de details.\n\n\n"
				+ "Cordialement.");
		return ret;
	}
	
	public HashMap<String,String> passChanged(User u){
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Modification de votre mot de passe");
		ret.put("body", "Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+",\n"
				+ "Nous vous informons par cette email que votre mot de passe a été modifié sur notre site web. \nSi vous n'avez pas demandé cette modification, veuillez contacter l'administration immédiatement.\n\n"
				+ "\n"
				+ "Si vous avez demandé la modification de votre mot de passe, alors aucune autre action n'est nécessaire.\n\n"
				+"\n"
				+ "Nous vous recommandons de conserver votre mot de passe en sécurité et de ne le partager avec personne.\n\n\n"
				+ "Cordialement.");
		return ret;
	}
	
	public HashMap<String,String> requestAwaiting(User u, Request r){
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("objet", "Vous avez une demande en attente");
		ret.put("body", "Bonjour "+ds.input_to_var(u, "db_pronom")+" "+u.getNom()+" "+u.getPrenom()+",\n"
				+ "Nous vous informons que vous avez une demande en attente d'action. \nVeuillez vous connecter à votre compte pour y accéder.\n\n"
				+ "\n"
				+ "Demande: "+r.getDoc_title()
				+"\n\n"
				+ "Par: "+ur.findById(r.getUser_id()).get().getNom() + " " + ur.findById(r.getUser_id()).get().getPrenom()+"\n\n\n"
				+ "Cordialement.");
		return ret;
	}
}
