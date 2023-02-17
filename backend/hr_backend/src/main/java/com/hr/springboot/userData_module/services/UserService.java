package com.hr.springboot.userData_module.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.service_module.models.Var;
import com.hr.springboot.service_module.services.DocService;
import com.hr.springboot.userData_module.models.Role;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.RoleRepo;
import com.hr.springboot.userData_module.repositories.TypeRepo;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private DocService ds;

	@Autowired
	private RoleRepo rr;
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private TypeRepo tr;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static final int max_attempts = 3;
	
	public void initRoleUser() {
		Type_personnel t1 = new Type_personnel();
		t1.setId("Enseignant");
		tr.save(t1);
		
		Type_personnel t2 = new Type_personnel();
		t2.setId("Administratif");
		tr.save(t2);
		
		Role userRole = new Role();
		userRole.setRoleName(CONSTS.USER_ROLE);
		userRole.setDesc("Default standard user role, shared between teachers and administratives");
		rr.save(userRole);
		
		Role l3 = new Role();
		l3.setRoleName(CONSTS.L3_ROLE);
		l3.setDesc("Admin role for layer3 authorities, should be used for HR admins");
		rr.save(l3);
		
		Role l2 = new Role();
		l2.setRoleName(CONSTS.L2_ROLE);
		l2.setDesc("Admin role for layer2 authorities, should be used for higher HR admin's supervisor");
		rr.save(l2);
		
		Role l1 = new Role();
		l1.setRoleName(CONSTS.L1_ROLE);
		l1.setDesc("Admin role for layer1 authorities, should be used for the highest authority");
		rr.save(l1);
		
		Role su = new Role();
		su.setRoleName(CONSTS.SU_ROLE);
		su.setDesc("Super user role");
		rr.save(su);
		
	
		User u1 = new User();
		u1.setNom("Hadiche");
		u1.setPrenom("Saad");
		u1.setGenre("masculin");
		u1.setEmail("user@gmail.com");
		u1.setPassword(getEncodedPassword("user"));
		u1.setPpr(12932);
		u1.setCin("BB124343");
		u1.setDate_naissance(LocalDate.of(2000, 10, 10));
		u1.setLieu_naissance("Casablanca");
		u1.setFonction_exerce("Professeur d'enseignement superieur");
		u1.setGrade("No clue");
		u1.setDate_affectation_enseignement(LocalDate.now());
		u1.setDate_affectation_MESRSFC(LocalDate.now());
		u1.setDate_effet_echelon(LocalDate.now());
		u1.setDate_effet_grade(LocalDate.now());
		u1.setDate_recrutement(LocalDate.now());
		u1.setAR_nom("حديش");
		u1.setAR_prenom("سعد");
		
		User u6 = new User();
		u6.setNom("Hadiche");
		u6.setPrenom("Saad");
		u6.setGenre("masculin");
		u6.setEmail("user2@gmail.com");
		u6.setPassword(getEncodedPassword("user2"));
		u6.setPpr(12932);
		u6.setCin("BB124343");
		u6.setDate_naissance(LocalDate.of(2000, 10, 10));
		u6.setLieu_naissance("Casablanca");
		u6.setFonction_exerce("Professeur d'enseignement superieur");
		u6.setGrade("No clue");
		u6.setDate_affectation_enseignement(LocalDate.now());
		u6.setDate_affectation_MESRSFC(LocalDate.now());
		u6.setDate_effet_echelon(LocalDate.now());
		u6.setDate_effet_grade(LocalDate.now());
		u6.setDate_recrutement(LocalDate.now());
		u6.setAR_nom("حديش");
		u6.setAR_prenom("سعد");
		

		Set<Role> temp1 = new HashSet<Role>();
		temp1.add(userRole);
		Set<Type_personnel> temp2 = new HashSet<Type_personnel>();
		temp2.add(t1);
		u1.setRole(temp1);
		u1.setType_personnel(temp2);
		ur.save(u1);
		
		Set<Role> temp12 = new HashSet<Role>();
		temp12.add(userRole);
		Set<Type_personnel> temp22 = new HashSet<Type_personnel>();
		temp22.add(t2);
		u6.setRole(temp12);
		u6.setType_personnel(temp22);
		ur.save(u6);
		
		User u2 = new User();
		u2.setEmail("layer3@gmail.com");
		u2.setPassword(getEncodedPassword("layer3"));
		u2.setNom("Bensada");
		u2.setPrenom("Yassine");
		u2.setGenre("masculin");
		u2.setPpr(12932);
		u2.setCin("BB124343");
		u2.setDate_naissance(LocalDate.of(2000, 10, 10));
		u2.setLieu_naissance("Oujda");
		u2.setFonction_exerce("Professeur d'enseignement superieur");
		u2.setGrade("No clue");
		u2.setDate_affectation_enseignement(LocalDate.now());
		u2.setDate_affectation_MESRSFC(LocalDate.now());
		u2.setDate_effet_echelon(LocalDate.now());
		u2.setDate_effet_grade(LocalDate.now());
		u2.setDate_recrutement(LocalDate.now());
		u2.setAR_nom("بنسادا");
		u2.setAR_prenom("يسين");
		Set<Role> temp3 = new HashSet<Role>();
		temp3.add(l3);
		Set<Type_personnel> temp4 = new HashSet<Type_personnel>();
		temp4.add(t2);
		u2.setRole(temp3);
		u2.setType_personnel(temp4);
		ur.save(u2);

		User u3 = new User();
		u3.setEmail("layer2@gmail.com");
		u3.setPassword(getEncodedPassword("layer2"));
		Set<Role> temp5 = new HashSet<Role>();
		temp5.add(l2);
		Set<Type_personnel> temp6 = new HashSet<Type_personnel>();
		temp6.add(t1);
		u3.setRole(temp5);
		u3.setType_personnel(temp6);
		ur.save(u3);
		
		User u4 = new User();
		u4.setEmail("layer1@gmail.com");
		u4.setPassword(getEncodedPassword("layer1"));
		Set<Role> temp7 = new HashSet<Role>();
		temp7.add(l1);
		Set<Type_personnel> temp8 = new HashSet<Type_personnel>();
		temp8.add(t1);
		temp8.add(t2);
		u4.setRole(temp7);
		u4.setType_personnel(temp8);
		ur.save(u4);
		
		User u5 = new User();
		u5.setEmail("root");
		u5.setPassword(getEncodedPassword("root"));
		Set<Role> temp9 = new HashSet<Role>();
		temp9.add(su);
		Set<Type_personnel> temp10 = new HashSet<Type_personnel>();
		temp10.add(t1);
		u5.setRole(temp9);
		u5.setType_personnel(temp10);
		ur.save(u5);
	}
	
	public User addUser(User user) {
		Role role = rr.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));

        return ur.save(user);
	}
	
	 public String getEncodedPassword(String password) {
	        return passwordEncoder.encode(password);
	    }
	 
	 public List<User> getAll(){
		 List<User> l = ur.findAll();
		 User n = ur.findByRole("superuser").get();
		 l.remove(n);
		 for(User u : l) {
			 u.setPassword("");
		 }
		 return l;
	 }
	 
	 public void increaseFailedAttempt(String email) {
		 User u = ur.authFindByMail(email).get();
		 int num = u.getLoginAttempts();
		 num++;
		 u.setLoginAttempts(num);
		 ur.save(u);
		 if(u.getLoginAttempts()>=3) lock(email);
	 }
	 
	 public void lock(String email) {
		 User u = ur.authFindByMail(email).get();
		 u.setLocked(true);
		 u.setLoginAttempts(0);
		 ur.save(u);
	 }
	 
	 public void resetAttempts(String email) {
		 User u = ur.authFindByMail(email).get();
		 u.setLoginAttempts(0);
		 ur.save(u);
	 }
	 
	 public void importByCIN(String filename) throws IOException, CsvException {
		 this.normalizeCsvTitles(filename);
		 InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+CONSTS.DOC_DIR+"/"+filename);
	     String csvAsString = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
	     JSONArray json = CDL.toJSONArray(csvAsString);
	     inputStream.close();
	     Role r = rr.findById("User").get();
	     HashSet<Role> hs = new HashSet<Role>();
	     hs.add(r);
	     for(int i=0;i<json.length(); i++) {
	    	 JSONObject temp = json.getJSONObject(i);
	    	 if(ur.findUserByCin(temp.getString("cin")).isEmpty()) {
				User u = new User();
				u.setEmail(temp.getString("email"));
				u.setCin(temp.getString("cin"));
				u.setRole(hs);
				ur.save(u);
				
			}else {
				System.out.println("collision escaping...");
			}
	     }
	     File myObj = new File(System.getProperty("user.dir")+CONSTS.DOC_DIR+"/"+filename); 
	     if (myObj.delete()) { 
	       System.out.println("Deleted the file: " + myObj.getName());
	     } else {
	       System.out.println("Failed to delete the file.");
	     } 
	 }
	 
	 public void mergeCinwData(String filename) throws IOException, NumberFormatException, CsvException {
		 this.normalizeCsvTitles(filename);
		 InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+CONSTS.DOC_DIR+"/"+filename);
	     String csvAsString = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
	     JSONArray json = CDL.toJSONArray(csvAsString);
	     inputStream.close();
	     for(int i=0;i<json.length(); i++) {
	    	 JSONObject temp = json.getJSONObject(i);
	    	 if(ur.findUserByCin(temp.getString("cin")).isPresent()) {
	    		 try {
	    			 String password = this.generateRandomPassword();
	    			 System.out.println(password);
					 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					 User u1 = ur.findUserByCin(temp.getString("cin")).get();
					 u1.setCode_etablissement(Integer.parseInt(temp.getString("code_etablissement")));
					 u1.setCode_annee(Integer.parseInt(temp.getString("code_annee")));
					 u1.setPpr(Integer.parseInt(temp.getString("ppr")));
					 u1.setNom(temp.getString("nom"));
					 u1.setPrenom(temp.getString("prenom"));
					 u1.setGenre(this.normalizeString(temp.getString("genre")));
					 u1.setDate_naissance(LocalDate.parse(temp.getString("date_naissance"),formatter));
					 u1.setLieu_naissance(temp.getString("lieu de naissance"));
					 u1.setCode_nationalite(temp.getString("code_nationalite"));
					 u1.setCode_grade(Integer.parseInt(temp.getString("code_grade")));
					 if(this.normalizeString(temp.getString("type_personnel")).equals("enseignant")) {
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
					u1.setDate_recrutement(LocalDate.parse(temp.getString("date_recrutement"),formatter));
					u1.setDate_affectation_MESRSFC(LocalDate.parse(temp.getString("date_affectation_mesrsfc"),formatter));
					u1.setDate_affectation_enseignement(LocalDate.parse(temp.getString("date_affectation_enseignement"),formatter));
					u1.setCode_departement(Integer.parseInt(temp.getString("code_departement")));
					u1.setNombre_diplomes(Integer.parseInt(temp.getString("nombre de diplome")));
					u1.setDiplome(temp.getString("diplome"));
					u1.setSpecialite(temp.getString("specialite"));
					u1.setUniv_etablissement_diplomate(temp.getString("universite_etablissement_diplomante"));
					u1.setFonction_exerce(temp.getString("fonction_exercee"));
					u1.setService_affectation(temp.getString("service_affectation"));
					u1.setGrade(temp.getString("grade"));
					u1.setDate_effet_grade(LocalDate.parse(temp.getString("date_effet_grade"),formatter));
					u1.setEchelon(temp.getString("echelon"));
					u1.setDate_effet_echelon(LocalDate.parse(temp.getString("date_effet_echelon"),formatter));
					Set<Role> srr = new HashSet<Role>();
					srr.add(rr.findById("User").get());
					u1.setRole(srr);
					u1.setNum_tel(temp.getString("num_tel"));
					u1.setDisabled(false);
					u1.setPassword(getEncodedPassword(password));
					ur.save(u1);
					System.out.println("Email: "+u1.getEmail());
					System.out.println("Password: "+u1.getPassword()); 
	    		 }catch(Exception e) {
	    		 System.out.println("Escaped: "+temp.getString("nom")+" "+temp.getString("prenom"));
	    		 }
	    		 
			 }else {
				 System.out.println("user non existing escaping...");
			 }
	     }
	     File myObj = new File(System.getProperty("user.dir")+CONSTS.DOC_DIR+"/"+filename); 
	     if (myObj.delete()) { 
	       System.out.println("Deleted the file: " + myObj.getName());
	     } else {
	       System.out.println("Failed to delete the file.");
	     } 
	 }
	 
	 private void normalizeCsvTitles(String filename) throws IOException, CsvException {
		File inputFile = new File(System.getProperty("user.dir")+CONSTS.DOC_DIR+"/"+filename);
		// Read existing file 
		CSVReader reader = new CSVReader(new FileReader(inputFile));
		List<String[]> csvBody = reader.readAll();
		// get CSV row column  and replace with by using row and column
		for(int i=0;i<csvBody.get(0).length;i++) {
			csvBody.get(0)[i] = this.normalizeString(csvBody.get(0)[i]);
		}
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	 }
	 
	 private String normalizeString(String s) {
		 s = s.toLowerCase();
		 s = Normalizer.normalize(s, Form.NFD);
		 s = s.replaceAll("\\p{M}", "");
		 s = s.trim();
		 return s;
	 }
	 
	 public String generateRandomPassword() {
		 byte[] array = new byte[8]; // length is bounded by 7
		 new Random().nextBytes(array);
		 String generatedString = new String(array, Charset.forName("UTF-8"));
		 return ds.encryptThisString(generatedString+LocalDateTime.now().toString());
	 }
	 
	 //tobeimplemented
	 public void importUsersCSV() {
		 
	 }
}
