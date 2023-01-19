package com.hr.springboot.userData_module.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name="User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int code_etablissement;
	private int code_annee;
	private int ppr;
	private String nom;
	private String prenom;
	private String genre;
	private String email;
	private String password;
	private Date date_naissance;
	private String lieu_naissance;
	private String code_nationalite;
	private int code_grade;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name="USER_TYPE",
		joinColumns = {
				@JoinColumn(name="USER_ID")
		},
		inverseJoinColumns = {
				@JoinColumn(name="TYPE_ID")
		}
	)
	private Set<Type_personnel> type_personnel;
	private Date date_recrutement;
	private Date date_affectation_MESRSFC;
	private Date date_affectation_enseignement;
	private int code_departement;
	private String departement;
	private int nombre_diplomes;
	private String diplome;
	private String specialite;
	private String univ_etablissement_diplomate;
	private String fonction_exerce;
	private String service_affectation;
	private String grade;
	private Date date_effet_grade;
	private String echelon;
	private Date date_effet_echelon;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name="USER_ROLE",
		joinColumns = {
				@JoinColumn(name="USER_ID")
		},
		inverseJoinColumns = {
				@JoinColumn(name="ROLE_ID")
		}
	)
	private Set<Role> role;
	private boolean disabled;
	
	public User() {
		this.date_naissance = new Date();
		this.date_recrutement = new Date();
		this.date_affectation_MESRSFC = new Date();
		this.date_affectation_enseignement = new Date();
		this.date_effet_echelon = new Date();
		this.date_effet_grade = new Date();
		this.disabled = false;
	}
	
	

	public User(int id, int code_etablissement, int code_annee, int ppr, String nom, String prenom, String genre,
			String email, String password, Date date_naissance, String lieu_naissance, String code_nationalite,
			int code_grade, Set<Type_personnel> type_personnel, Date date_recrutement, Date date_affectation_MESRSFC,
			Date date_affectation_enseignement, int code_departement, String departement, int nombre_diplomes,
			String diplome, String specialite, String univ_etablissement_diplomate, String fonction_exerce,
			String service_affectation, String grade, Date date_effet_grade, String echelon, Date date_effet_echelon,
			Set<Role> role, boolean disabled) {
		super();
		this.id = id;
		this.code_etablissement = code_etablissement;
		this.code_annee = code_annee;
		this.ppr = ppr;
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.email = email;
		this.password = password;
		this.date_naissance = date_naissance;
		this.lieu_naissance = lieu_naissance;
		this.code_nationalite = code_nationalite;
		this.code_grade = code_grade;
		this.type_personnel = type_personnel;
		this.date_recrutement = date_recrutement;
		this.date_affectation_MESRSFC = date_affectation_MESRSFC;
		this.date_affectation_enseignement = date_affectation_enseignement;
		this.code_departement = code_departement;
		this.departement = departement;
		this.nombre_diplomes = nombre_diplomes;
		this.diplome = diplome;
		this.specialite = specialite;
		this.univ_etablissement_diplomate = univ_etablissement_diplomate;
		this.fonction_exerce = fonction_exerce;
		this.service_affectation = service_affectation;
		this.grade = grade;
		this.date_effet_grade = date_effet_grade;
		this.echelon = echelon;
		this.date_effet_echelon = date_effet_echelon;
		this.role = role;
		this.disabled = disabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCode_etablissement() {
		return code_etablissement;
	}

	public void setCode_etablissement(int code_etablissement) {
		this.code_etablissement = code_etablissement;
	}

	public int getCode_annee() {
		return code_annee;
	}

	public void setCode_annee(int code_annee) {
		this.code_annee = code_annee;
	}

	public int getPpr() {
		return ppr;
	}

	public void setPpr(int ppr) {
		this.ppr = ppr;
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

	public Date getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}

	public String getLieu_naissance() {
		return lieu_naissance;
	}

	public void setLieu_naissance(String lieu_naissance) {
		this.lieu_naissance = lieu_naissance;
	}

	public String getCode_nationalite() {
		return code_nationalite;
	}

	public void setCode_nationalite(String code_nationalite) {
		this.code_nationalite = code_nationalite;
	}

	public int getCode_grade() {
		return code_grade;
	}

	public void setCode_grade(int code_grade) {
		this.code_grade = code_grade;
	}

	public Date getDate_recrutement() {
		return date_recrutement;
	}

	public void setDate_recrutement(Date date_recrutement) {
		this.date_recrutement = date_recrutement;
	}

	public Date getDate_affectation_MESRSFC() {
		return date_affectation_MESRSFC;
	}

	public void setDate_affectation_MESRSFC(Date date_affectation_MESRSFC) {
		this.date_affectation_MESRSFC = date_affectation_MESRSFC;
	}

	public Date getDate_affectation_enseignement() {
		return date_affectation_enseignement;
	}

	public void setDate_affectation_enseignement(Date date_affectation_enseignement) {
		this.date_affectation_enseignement = date_affectation_enseignement;
	}

	public int getCode_departement() {
		return code_departement;
	}

	public void setCode_departement(int code_departement) {
		this.code_departement = code_departement;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public int getNombre_diplomes() {
		return nombre_diplomes;
	}

	public void setNombre_diplomes(int nombre_diplomes) {
		this.nombre_diplomes = nombre_diplomes;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getUniv_etablissement_diplomate() {
		return univ_etablissement_diplomate;
	}

	public void setUniv_etablissement_diplomate(String univ_etablissement_diplomate) {
		this.univ_etablissement_diplomate = univ_etablissement_diplomate;
	}

	public String getFonction_exerce() {
		return fonction_exerce;
	}

	public void setFonction_exerce(String fonction_exerce) {
		this.fonction_exerce = fonction_exerce;
	}

	public String getService_affectation() {
		return service_affectation;
	}

	public void setService_affectation(String service_affectation) {
		this.service_affectation = service_affectation;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getDate_effet_grade() {
		return date_effet_grade;
	}

	public void setDate_effet_grade(Date date_effet_grade) {
		this.date_effet_grade = date_effet_grade;
	}

	public String getEchelon() {
		return echelon;
	}

	public void setEchelon(String echelon) {
		this.echelon = echelon;
	}

	public Date getDate_effet_echelon() {
		return date_effet_echelon;
	}

	public void setDate_effet_echelon(Date date_effet_echelon) {
		this.date_effet_echelon = date_effet_echelon;
	}

	public Set<Type_personnel> getType_personnel() {
		return type_personnel;
	}

	public void setType_personnel(Set<Type_personnel> type_personnel) {
		this.type_personnel = type_personnel;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
