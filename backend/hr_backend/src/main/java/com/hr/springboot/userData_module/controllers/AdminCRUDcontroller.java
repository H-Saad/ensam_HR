package com.hr.springboot.userData_module.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.springboot.mailing_module.services.MailDict;
import com.hr.springboot.mailing_module.services.MailService;
import com.hr.springboot.service_module.services.DocService;
import com.hr.springboot.userData_module.models.Role;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.RoleRepo;
import com.hr.springboot.userData_module.repositories.TypeRepo;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.userData_module.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("CRUD")
public class AdminCRUDcontroller {

	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private TypeRepo tr;
	
	@Autowired
	private RoleRepo rr;
	
	@Autowired
	private DocService ds;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private MailService ms;
	
	@Autowired
	private MailDict md;
	
	
	/*
	 * "genre":"M" OR "F"
	 * "type_personnel":"E" OR "A"
	 * all dates follow this format: 2007-12-17
	 */
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("add")
	public ResponseEntity<HashMap<String,String>> add(@RequestBody HashMap<String,String> req){
		User u1 = new User();
		String password = "";
		u1.setCode_etablissement(Integer.parseInt(req.get("code_etablissement")));
		u1.setCode_annee(Integer.parseInt(req.get("code_annee")));
		u1.setNom(req.get("nom"));
		u1.setPrenom(req.get("prenom"));
		u1.setNum_tel(req.get("num_tel"));
		if(req.get("genre").toUpperCase().equals("M")) {
			u1.setGenre("masculin");
		}else {
			u1.setGenre("feminin");
		}
		u1.setEmail(req.get("email"));
		u1.setPassword("");
		u1.setPpr(Integer.parseInt(req.get("ppr")));
		u1.setCin(req.get("cin"));
		u1.setDate_naissance(LocalDate.parse(req.get("date_naissance")));
		u1.setLieu_naissance(req.get("lieu_naissance"));
		u1.setCode_nationalite(req.get("code_nationalite"));
		u1.setCode_grade(Integer.parseInt(req.get("code_grade")));
		if(req.get("type_personnel").toUpperCase().equals("E")) {
			Type_personnel t = tr.findById("Enseignant").get();
			Set<Type_personnel> s = new HashSet<Type_personnel>();
			s.add(t);
			u1.setType_personnel(s);
		}else {
			Type_personnel t = tr.findById("Administratif").get();
			Set<Type_personnel> s = new HashSet<Type_personnel>();
			s.add(t);
			u1.setType_personnel(s);
		}
		u1.setDate_recrutement(LocalDate.parse(req.get("date_recrutement")));
		u1.setDate_affectation_MESRSFC(LocalDate.parse(req.get("date_affectation_MESRSFC")));
		u1.setDate_affectation_enseignement(LocalDate.parse(req.get("date_affectation_enseignement")));
		u1.setCode_departement(Integer.parseInt(req.get("code_departement")));
		u1.setDepartement(req.get("departement"));
		u1.setNombre_diplomes(Integer.parseInt(req.get("nombre_diplomes")));
		u1.setDiplome(req.get("diplome"));
		u1.setSpecialite(req.get("specialite"));
		u1.setUniv_etablissement_diplomate(req.get("univ_etablissement_diplomante"));
		u1.setFonction_exerce(req.get("fonction_exerce"));
		u1.setService_affectation(req.get("service_affectation"));
		u1.setGrade(req.get("grade"));
		u1.setDate_effet_grade(LocalDate.parse(req.get("date_effet_grade")));
		u1.setEchelon(req.get("echelon"));
		u1.setDate_effet_echelon(LocalDate.parse(req.get("date_effet_echelon")));
		u1.setAdresse(req.get("adresse"));
		
		Set<Role> srr = new HashSet<Role>();
		srr.add(rr.findById("User").get());
		u1.setRole(srr);
		
		u1.setDisabled(false);
		u1.setAR_nom(req.get("AR_nom"));
		u1.setAR_prenom(req.get("AR_prenom"));
		password = ds.encryptThisString(u1.getEmail()+LocalDateTime.now().toString());
		u1.setPassword(passwordEncoder.encode(password));
		
		ur.save(u1);
		
		ms.sendmail(u1, md.accCreated(u1, password));
		
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("Password", password);
		return ResponseEntity.status(200).body(ret);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("get/{id}")
	public ResponseEntity<User> get(@PathVariable int id){
		User u = ur.findById(id).get();
		return ResponseEntity.status(200).body(u);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getAll")
	public ResponseEntity<List<User>> getAll(){
		List<User> ret = new ArrayList<User>();
		List<User> req = ur.findAll();
		Role r = rr.findById("User").get();
		for(User u:req) {
			if(!u.isDisabled() && u.getRole().contains(r)) {
				u.setPassword("");
				ret.add(u);
			}
			
		}
		return ResponseEntity.status(200).body(ret);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("update")
	public ResponseEntity<HashMap<String,String>> update(@RequestBody HashMap<String,String> req){
		User u1 = ur.findById(Integer.parseInt(req.get("id"))).get();
		u1.setCode_etablissement(Integer.parseInt(req.get("code_etablissement")));
		u1.setCode_annee(Integer.parseInt(req.get("code_annee")));
		u1.setNom(req.get("nom"));
		u1.setPrenom(req.get("prenom"));
		u1.setNum_tel(req.get("num_tel"));
		if(req.get("genre").toUpperCase().equals("M")) {
			u1.setGenre("masculim");
		}else {
			u1.setGenre("feminin");
		}
		u1.setEmail(req.get("email"));
		u1.setPassword("");
		u1.setPpr(Integer.parseInt(req.get("ppr")));
		u1.setCin(req.get("cin"));
		u1.setDate_naissance(LocalDate.parse(req.get("date_naissance")));
		u1.setLieu_naissance(req.get("lieu_naissance"));
		u1.setCode_nationalite(req.get("code_nationalite"));
		u1.setCode_grade(Integer.parseInt(req.get("code_grade")));
		if(req.get("type_personnel").toUpperCase().equals("E")) {
			Type_personnel t = tr.findById("Enseignant").get();
			Set<Type_personnel> s = new HashSet<Type_personnel>();
			s.add(t);
			u1.setType_personnel(s);
		}else {
			Type_personnel t = tr.findById("Administratif").get();
			Set<Type_personnel> s = new HashSet<Type_personnel>();
			s.add(t);
			u1.setType_personnel(s);
		}
		u1.setDate_recrutement(LocalDate.parse(req.get("date_recrutement")));
		u1.setDate_affectation_MESRSFC(LocalDate.parse(req.get("date_affectation_MESRSFC")));
		u1.setDate_affectation_enseignement(LocalDate.parse(req.get("date_affectation_enseignement")));
		u1.setCode_departement(Integer.parseInt(req.get("code_departement")));
		u1.setDepartement(req.get("departement"));
		u1.setNombre_diplomes(Integer.parseInt(req.get("nombre_diplomes")));
		u1.setDiplome(req.get("diplome"));
		u1.setSpecialite(req.get("specialite"));
		u1.setUniv_etablissement_diplomate(req.get("univ_etablissement_diplomante"));
		u1.setFonction_exerce(req.get("fonction_exerce"));
		u1.setService_affectation(req.get("service_affectation"));
		u1.setGrade(req.get("grade"));
		u1.setDate_effet_grade(LocalDate.parse(req.get("date_effet_grade")));
		u1.setEchelon(req.get("echelon"));
		u1.setDate_effet_echelon(LocalDate.parse(req.get("date_effet_echelon")));
		
		Set<Role> srr = new HashSet<Role>();
		srr.add(rr.findById("User").get());
		u1.setRole(srr);
		
		u1.setAR_nom(req.get("AR_nom"));
		u1.setAR_prenom(req.get("AR_prenom"));
		
		ur.save(u1);
		
		HashMap<String,String> ret = new HashMap<String,String>();
		ret.put("status", "success");
		return ResponseEntity.status(200).body(ret);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("delete/{id}/{motif}")
	public ResponseEntity<HashMap<String,String>> delete(@PathVariable int id, @PathVariable String motif){
		User u = ur.findById(id).get();
		u.setDisabled(true);
		u.setDisable_cause(motif);
		ur.save(u);
		HashMap<String,String> h = new HashMap<String,String>();
		h.put("status", "success");
		return ResponseEntity.status(200).body(h);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("showDisabled")
	public ResponseEntity<List<User>> showDisabled(){
		return ResponseEntity.status(200).body(ur.findAllDisabled());
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("deleteDisabled/{id}")
	public ResponseEntity<HashMap<String,String>> deleteDisabled(@PathVariable int id){
		User u = ur.findById(id).get();
		HashMap<String,String> ret = new HashMap<String,String>();
		if(u.isDisabled()) {
			ur.delete(u);
			ret.put("status", "success");
			return ResponseEntity.status(200).body(ret);
		}
		return ResponseEntity.status(400).body(ret);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("restoreDisabled/{id}")
	public ResponseEntity<HashMap<String,String>> restoreDisabled(@PathVariable int id){
		User u = ur.findById(id).get();
		HashMap<String,String> ret = new HashMap<String,String>();
		u.setDisabled(false);
		ret.put("status", "success");
		return ResponseEntity.status(200).body(ret);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("import_mails")
	public ResponseEntity<String> importMails(@RequestBody HashMap<String,String> req){
		String filename = req.get("filename");
		try {
			us.importByCIN(filename);
			return ResponseEntity.status(200).body("ok");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body("bad csv file");
		}
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("import_users")
	public ResponseEntity<String> importUsers(@RequestBody HashMap<String,String> req){
		String filename = req.get("filename");
		try {
			us.mergeCinwData(filename);
			return ResponseEntity.status(200).body("ok");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body("bad csv file");
		}
	}
}
