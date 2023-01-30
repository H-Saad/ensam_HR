package com.hr.springboot.mailing_module.controllers;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.notification_module.models.Notification;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.userData_module.models.User;

@RestController
@RequestMapping("notif")
public class NotifController {
	
	@Autowired
	private NotifService ns;
	
	@Autowired
	private JwtUtil util;
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getAll")
	public List<Notification> getall(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		return ns.getAllByUserID(u);
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("readOne")
	public ResponseEntity<JSONObject> readOne(@RequestBody HashMap<String, String> req){
		ns.markRead(Integer.parseInt(req.get("id")));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("readAll")
	public ResponseEntity<JSONObject> readAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		ns.markAllAsRead(u);
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("deleteOne")
	public ResponseEntity<JSONObject> deleteOne(@RequestBody HashMap<String, String> req){
		ns.deleteSingle(Integer.parseInt(req.get("id")));
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("deleteAll")
	public ResponseEntity<JSONObject> deleteAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		User u = util.getUserfromToken(auth);
		ns.deleteAllByUser(u);
		return ResponseEntity.status(200).body(new JSONObject());
	}
	
}
