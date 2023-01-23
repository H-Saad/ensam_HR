package com.hr.springboot.service_module.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;

@Service
public class Doc_service {
	
	@Autowired
	UserRepo ur;
	
	public String input_to_var(User u, String input) {
		
		User director = ur.findByRole("layer1_Admin").get();
		User sec = ur.findByRole("layer2_Admin").get();
		User rh = ur.findByRole("layer3_Admin").get();
		
		switch(input.toLowerCase()) {
		case "db_codeetab":
			return ""+u.getCode_etablissement();
		case "db_codeannee":
			return ""+u.getCode_annee();
		case "db_ppr":
			return ""+u.getPpr();
		case "db_nom":
			return u.getNom();
		case "db_prenom":
			return u.getPrenom();
		case "db_fullname":
			return u.getNom() + " " + u.getPrenom();
		case "db_genre":
			return u.getGenre();
		case "db_email":
			return u.getEmail();
		case "db_cin":
			return u.getCin();
		case "db_daten":
			return u.getDate_naissance().toString();
		case "db_lieun":
			return u.getLieu_naissance();
		case "db_coden":
			return u.getCode_nationalite();
		case "db_codeg":
			return ""+u.getCode_grade();
		case "db_typepers":
			Set<Type_personnel> t = u.getType_personnel();
			String ret = "";
			for(Type_personnel ty : t) {
				ret = ret + " " +ty.getType();
			}
			return ret;
		case "db_dater":
			return u.getDate_recrutement().toString();
		case "db_dateaffm":
			return u.getDate_affectation_MESRSFC().toString();
		case "db_dateaffe":
			return u.getDate_affectation_enseignement().toString();
		case "db_coded":
			return ""+u.getCode_departement();
		case "db_dept":
			return u.getDepartement();
		case "db_ndip":
			return ""+u.getNombre_diplomes();
		case "db_dip":
			return u.getDiplome();
		case "db_spe":
			return u.getSpecialite();
		case "db_univ":
			return u.getUniv_etablissement_diplomate();
		case "db_fcte":
			return u.getFonction_exerce();
		case "db_serva":
			return u.getService_affectation();
		case "db_grade":
			return u.getGrade();
		case "db_echelon":
			return u.getEchelon();
		case "db_dateech":
			return u.getDate_effet_echelon().toString();
		case "db_arnom":
			return u.getAR_nom();
		case "db_arprenom":
			return u.getAR_prenom();
		case "db_arfullname":
			return u.getAR_nom() + " " +u.getAR_prenom();
		case "db_directnom":
			return director.getNom();
		case "db_directprenom":
			return director.getPrenom();
		case "db_directfull":
			return director.getNom() + " " + director.getPrenom();
		case "db_ardirectnom":
			return director.getAR_nom();
		case "db_ardirectprenom":
			return director.getAR_prenom();
		case "db_ardirectfull":
			return director.getAR_nom() + " " + director.getAR_prenom();
		case "db_secnom":
			return sec.getNom();
		case "db_secprenom":
			return sec.getPrenom();
		case "db_secfull":
			return sec.getNom() + " " + sec.getPrenom();
		case "db_arsecnom":
			return sec.getAR_nom();
		case "db_arsecprenom":
			return sec.getAR_prenom();
		case "db_arsecfull":
			return sec.getAR_nom() + " " + sec.getAR_prenom();
		case "db_rhnom":
			return rh.getNom();
		case "db_rhprenom":
			return rh.getPrenom();
		case "db_rhfull":
			return rh.getNom() + " " + rh.getPrenom();
		case "db_arrhnom":
			return rh.getAR_nom();
		case "db_arrhprenom":
			return rh.getAR_prenom();
		case "db_arrhfull":
			return rh.getAR_nom() + " " + rh.getAR_prenom();
		case "db_pronom":
			if(u.getGenre().toLowerCase().equals("masculin")) return "Mr";
			else return "Mme";
		case "db_numtel":
			return u.getNum_tel();
		default:
			return "";
		}
	}
}
