package com.hr.springboot.validation_module.controllers;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.notification_module.services.NotifDict;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.validation_module.models.Completed_request;
import com.hr.springboot.validation_module.models.Pending_request;
import com.hr.springboot.validation_module.models.Refused_request;
import com.hr.springboot.validation_module.repositories.CompletedRepo;
import com.hr.springboot.validation_module.repositories.PendingRepo;
import com.hr.springboot.validation_module.repositories.RefusedRepo;
import com.hr.springboot.validation_module.services.ValidationService;

@RestController
@RequestMapping("validation")
public class ValidationController {
	
	@Autowired
	private ValidationService vs;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private PendingRepo pr;
	
	@Autowired
	private RefusedRepo rr;
	
	@Autowired
	private CompletedRepo cr;
	
	@Autowired
	private NotifService ns;
	
	@Autowired
	private NotifDict nd;
	
	@Autowired
	private UserRepo ur;
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("UserRefused")
	List<Refused_request> getuserrefused(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		return vs.getUserRefused(u);
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("UserCompleted")
	List<Completed_request> getusercompleted(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		return vs.getUserCompleted(u);
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("layerRefused")
	List<Refused_request> getlayerRefused(){
		return rr.findAll();
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("layerCompleted")
	List<Completed_request> getlayercompleted(){
		return cr.findAll();
	}

	@PreAuthorize("hasRole('User')")
	@GetMapping("UserPending")
	public List<Pending_request> userpending(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		return vs.getUserPending(u);
	}
	
	@PreAuthorize("hasRole('layer3')")
	@GetMapping("layer3Pending")
	public List<Pending_request> l3pending(){
		return vs.getL3Pending();
	}
	
	@PreAuthorize("hasRole('layer2')")
	@GetMapping("layer2Pending")
	public List<Pending_request> l2pending(){
		return vs.getL2Pending();
	}
	
	@PreAuthorize("hasRole('layer1')")
	@GetMapping("layer1Pending")
	public List<Pending_request> l1pending(){
		return vs.getL1Pending();
	}
	
	@PreAuthorize("hasRole('layer3')")
	@GetMapping("layer3Accept/{id}")
	public ResponseEntity<JSONObject> l3Accept(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(id).get();
		vs.validatel3(p, u);
		//notifier l'utilisateur qu'il a approuve une requete
		ns.makeNotif(u, u, p, nd.approve(p));
		//notifier next layer qu'une requete l'attends
		ns.makeNotif(u, ur.findByRole("layer2").get(), p, nd.pendingReq(p));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	@PreAuthorize("hasRole('layer3')")
	@PostMapping("layer3Refuse")
	public ResponseEntity<JSONObject> l3Refuse(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(Integer.parseInt(req.get("id"))).get();
		vs.refuseRequest(p, u, req.get("refusal_msg"));
		//notifier l utilisateur qu il a refuse une requete
		ns.makeNotif(u, u, p, nd.refuse(p));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	
	@PreAuthorize("hasRole('layer2')")
	@GetMapping("layer2Accept/{id}")
	public ResponseEntity<JSONObject> l2Accept(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(id).get();
		vs.validatel2(p, u);
		ns.makeNotif(u, u, p, nd.approve(p));
		//notifier next layer qu'une requete l'attends
		ns.makeNotif(u, ur.findByRole("layer1").get(), p, nd.pendingReq(p));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	@PreAuthorize("hasRole('layer2')")
	@PostMapping("layer2Refuse")
	public ResponseEntity<JSONObject> l2Refuse(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(Integer.parseInt(req.get("id"))).get();
		vs.refuseRequest(p, u, req.get("refusal_msg"));
		ns.makeNotif(u, u, p, nd.refuse(p));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	
	@PreAuthorize("hasRole('layer1')")
	@GetMapping("layer1Accept/{id}")
	public ResponseEntity<JSONObject> l1Accept(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @PathVariable int id){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(id).get();
		vs.validatel1(p, u);
		ns.makeNotif(u, u, p, nd.approve(p));
		p = pr.findById(p.getId()).get();
		if(p.isApproved_by_l1() && p.isApproved_by_l2() && p.isApproved_by_l3()) {
			vs.completeRequest(p);
			return ResponseEntity.status(200).body(new JSONObject());
		}
		return ResponseEntity.status(401).body(new JSONObject());
	}
	
	@PreAuthorize("hasRole('layer1')")
	@PostMapping("layer1Refuse")
	public ResponseEntity<JSONObject> l1Refuse(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(Integer.parseInt(req.get("id"))).get();
		vs.refuseRequest(p, u, req.get("refusal_msg"));
		ns.makeNotif(u, u, p, nd.refuse(p));
		return ResponseEntity.status(200).body(new JSONObject());
	}
}
