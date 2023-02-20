package com.hr.springboot.validation_module.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.notification_module.services.NotifDict;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.service_module.repositories.DocRepo;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.validation_module.models.Completed_request;
import com.hr.springboot.validation_module.models.Pending_request;
import com.hr.springboot.validation_module.models.Refused_request;
import com.hr.springboot.validation_module.repositories.CompletedRepo;
import com.hr.springboot.validation_module.repositories.PendingRepo;
import com.hr.springboot.validation_module.repositories.RefusedRepo;
import com.hr.springboot.validation_module.services.ValidationService;

@CrossOrigin
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
	
	@Autowired
	private DocRepo dr;
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("UserRefused")
	public List<HashMap<String,Object>> getuserrefused(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		List<Refused_request> lr = vs.getUserRefused(u);
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Refused_request rr : lr) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("refused_request", rr);
			temp.put("source_user", ur.findById(rr.getUser_id()).get());
			temp.put("document", dr.findById(rr.getDocument_id()).get());
			temp.put("refused_by_user", ur.findById(rr.getUser_id()).get());
			ret.add(temp);
		}
		return ret;
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("UserCompleted")
	public List<HashMap<String,Object>> getusercompleted(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		List<Completed_request> lc = vs.getUserCompleted(u);
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Completed_request cr: lc) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("completed_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("layerRefused")
	public List<HashMap<String,Object>> getlayerRefused(){
		List<Refused_request> lr = rr.findAll();
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Refused_request rr : lr) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("refused_request", rr);
			temp.put("source_user", ur.findById(rr.getUser_id()).get());
			temp.put("document", dr.findById(rr.getDocument_id()).get());
			temp.put("refused_by_user", ur.findById(rr.getUser_id()).get());
			ret.add(temp);
		}
		return ret;
	}
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("layerCompleted")
	public List<HashMap<String,Object>> getlayercompleted(){
		List<Completed_request> lc = cr.findAll();
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Completed_request cr: lc) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("completed_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;
	}

	@PreAuthorize("hasRole('User')")
	@GetMapping("UserPending")
	public List<HashMap<String,Object>> userpending(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		List<Pending_request> lp = vs.getUserPending(u);
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Pending_request cr: lp) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("pending_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;
	}
	
	@PreAuthorize("hasRole('layer3')")
	@GetMapping("layer3Pending")
	public List<HashMap<String,Object>> l3pending(){
		List<Pending_request> lp = vs.getL3Pending();
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Pending_request cr: lp) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("pending_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;
	}
	
	@PreAuthorize("hasRole('layer2')")
	@GetMapping("layer2Pending")
	public List<HashMap<String,Object>> l2pending(){
		List<Pending_request> lp = vs.getL2Pending();
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Pending_request cr: lp) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("pending_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;
	}
	
	@PreAuthorize("hasRole('layer1')")
	@GetMapping("layer1Pending")
	public List<HashMap<String,Object>> l1pending(){
		List<Pending_request> lp = vs.getL1Pending();
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Pending_request cr: lp) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("pending_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;	
	}
	
	@PreAuthorize("hasRole('layer3')")
	@PostMapping("layer3Accept")
	/*
	 * For every layer accept api
	 * post request body:
	 * json {
	 * 			"id" : "id",
	 * 			"filename" : "filename"
	 * 		}
	 */
	public ResponseEntity<JSONObject> l3Accept(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(Integer.parseInt(req.get("id"))).get();
		vs.validatel3(p, u, req.get("filename"));
		//notifier l'utilisateur qu'il a approuve une requete
		ns.makeNotif(u, u, p, nd.approve(p));
		//notifier next layer qu'une requete l'attends
		ns.makeNotif(u, ur.findByRole("layer2").get(), p, nd.pendingReq(p));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	/*
	 * For every layer refuse api
	 * post request body:
	 * json {
	 * 			"id" : "id",
	 * 			"refusal_msg" : "refusal_msg"
	 * 		}
	 */
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
	@PostMapping("layer2Accept")
	public ResponseEntity<JSONObject> l2Accept(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,  @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(Integer.parseInt(req.get("id"))).get();
		vs.validatel2(p, u, req.get("filename"));
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
	@PostMapping("layer1Accept")
	public ResponseEntity<JSONObject> l1Accept(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,String> req){
		User u = util.getUserfromToken(auth);
		Pending_request p = pr.findById(Integer.parseInt(req.get("id"))).get();
		vs.validatel1(p, u, req.get("filename"));
		ns.makeNotif(u, u, p, nd.approve(p));
		p = pr.findById(p.getId()).get();
		if(p.isApproved_by_l1() && p.isApproved_by_l2() && p.isApproved_by_l3()) {
			vs.completeRequest(p);
			return ResponseEntity.status(200).body(new JSONObject());
		}
		return ResponseEntity.status(400).body(new JSONObject());
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
	
	@PreAuthorize("hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("allPending")
	public List<HashMap<String,Object>> allpending(){
		List<Pending_request> lp = pr.findAll();
		List<HashMap<String,Object>> ret = new ArrayList<HashMap<String,Object>>();
		for(Pending_request cr: lp) {
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("pending_request", cr);
			temp.put("source_user", ur.findById(cr.getUser_id()).get());
			temp.put("document", dr.findById(cr.getDocument_id()).get());
			ret.add(temp);
		}
		return ret;
	}
}
