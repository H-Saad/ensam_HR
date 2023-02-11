package com.hr.springboot.userData_module.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grade {
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
	private LocalDate date_effet_grade;
	private int code_grade;
	private String grade;
	private String echelon;
	private LocalDate date_effet_echelon;
	private boolean exceptionel; 
	private boolean rapide;
	private boolean normal;
	
	public Grade() {
		this.exceptionel = false;
		this.rapide = false;
		this.normal = false;
	}

	public Grade(String email, String nom, String prenom, String genre, String cin, LocalDate date_naissance,
			String lieu_naissance, LocalDate date_recrutement, String num_tel, String type_pers,
			LocalDate date_effet_grade, int code_grade, String grade, String echelon, LocalDate date_effet_echelon,
			boolean exceptionel, boolean rapide, boolean normal) {
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
		this.date_effet_grade = date_effet_grade;
		this.code_grade = code_grade;
		this.grade = grade;
		this.echelon = echelon;
		this.date_effet_echelon = date_effet_echelon;
		this.exceptionel = exceptionel;
		this.rapide = rapide;
		this.normal = normal;
	}



	public boolean isExceptionel() {
		return exceptionel;
	}



	public void setExceptionel(boolean exceptionel) {
		this.exceptionel = exceptionel;
	}



	public boolean isRapide() {
		return rapide;
	}



	public void setRapide(boolean rapide) {
		this.rapide = rapide;
	}



	public boolean isNormal() {
		return normal;
	}



	public void setNormal(boolean normal) {
		this.normal = normal;
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

	public LocalDate getDate_effet_grade() {
		return date_effet_grade;
	}

	public void setDate_effet_grade(LocalDate date_effet_grade) {
		this.date_effet_grade = date_effet_grade;
	}

	public int getCode_grade() {
		return code_grade;
	}

	public void setCode_grade(int code_grade) {
		this.code_grade = code_grade;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEchelon() {
		return echelon;
	}

	public void setEchelon(String echelon) {
		this.echelon = echelon;
	}

	public LocalDate getDate_effet_echelon() {
		return date_effet_echelon;
	}

	public void setDate_effet_echelon(LocalDate date_effet_echelon) {
		this.date_effet_echelon = date_effet_echelon;
	}
	
}
