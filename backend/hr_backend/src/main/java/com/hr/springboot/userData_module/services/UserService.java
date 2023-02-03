package com.hr.springboot.userData_module.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.userData_module.models.Role;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.RoleRepo;
import com.hr.springboot.userData_module.repositories.TypeRepo;
import com.hr.springboot.userData_module.repositories.UserRepo;

@Service
public class UserService {

	@Autowired
	private RoleRepo rr;
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private TypeRepo tr;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void initRoleUser() {
		Type_personnel t1 = new Type_personnel();
		t1.setType("Enseignant");
		tr.save(t1);
		
		Type_personnel t2 = new Type_personnel();
		t2.setType("Administratif");
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

		Set<Role> temp1 = new HashSet<Role>();
		temp1.add(userRole);
		Set<Type_personnel> temp2 = new HashSet<Type_personnel>();
		temp2.add(t1);
		u1.setRole(temp1);
		u1.setType_personnel(temp2);
		ur.save(u1);
		
		User u2 = new User();
		u2.setEmail("layer3@gmail.com");
		u2.setPassword(getEncodedPassword("layer3"));
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
	 
	 //tobeimplemented
	 public void importUsersCSV() {
		 
	 }
	 
	 //tobeimplemented
	 public void exportUserCSV() {
		 
	 }
}
