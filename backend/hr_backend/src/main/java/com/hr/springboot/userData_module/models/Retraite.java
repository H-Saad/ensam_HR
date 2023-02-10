package com.hr.springboot.userData_module.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Retraite {
	@Id
	private String email;
	private String nom;
	private String prenom;
	private String genre;
	private String cin;
	private LocalDate date_naissance;
	private String lieu_naissance;
	private LocalDate date_recrutement;
	private String num_tel;
	private String type_pers;
	
	public Retraite() {
		
	}

	public Retraite(String email, String nom, String prenom, String genre, String cin, LocalDate date_naissance,
			String lieu_naissance, LocalDate date_recrutement, String num_tel, String type_pers) {
		super();
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.cin = cin;
		this.date_naissance = date_naissance;
		this.lieu_naissance = lieu_naissance;
		this.date_recrutement = date_recrutement;
		this.num_tel = num_tel;
		this.type_pers = type_pers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public LocalDate getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(LocalDate date_naissance) {
		this.date_naissance = date_naissance;
	}

	public String getLieu_naissance() {
		return lieu_naissance;
	}

	public void setLieu_naissance(String lieu_naissance) {
		this.lieu_naissance = lieu_naissance;
	}

	public LocalDate getDate_recrutement() {
		return date_recrutement;
	}

	public void setDate_recrutement(LocalDate date_recrutement) {
		this.date_recrutement = date_recrutement;
	}

	public String getNum_tel() {
		return num_tel;
	}

	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
	}

	public String getType_pers() {
		return type_pers;
	}

	public void setType_pers(String type_pers) {
		this.type_pers = type_pers;
	}
	
}
