package com.hr.springboot.service_module.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
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

import com.google.common.net.HttpHeaders;
import com.hr.springboot.jwt.util.JwtUtil;
import com.hr.springboot.notification_module.services.NotifDict;
import com.hr.springboot.notification_module.services.NotifService;
import com.hr.springboot.service_module.models.Document;
import com.hr.springboot.service_module.repositories.DocRepo;
import com.hr.springboot.service_module.services.DocService;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.validation_module.models.Pending_request;
import com.hr.springboot.validation_module.models.Request;
import com.hr.springboot.validation_module.services.ValidationService;
import com.opencsv.exceptions.CsvValidationException;

@CrossOrigin
@RestController
@RequestMapping("docs")
public class DocController {
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private DocService ds;
	
	@Autowired
	private DocRepo dr;
	
	@Autowired
	private ValidationService vs;
	
	@Autowired
	private NotifService ns;
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private NotifDict nd;

	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getAllowed")
	public ResponseEntity<ArrayList<HashMap<String,String>>> getDocs(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
		List<Document> l =ds.getDocList(util.getUserAuthority(auth));
		ArrayList<HashMap<String,String>> jl = new ArrayList<HashMap<String,String>>();
		for(Document d : l) {
			HashMap<String,String> temp = new HashMap<String,String>();
			temp.put("id", ""+d.getId());
			temp.put("title", d.getTitle());
			temp.put("needs_form", Boolean.toString(d.isNeeds_form()));
			jl.add(temp);
		}
		return ResponseEntity.status(200).body(jl);
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getFormVars/{doc_id}")
	public ResponseEntity<ArrayList<HashMap<String,String>>> getFormVars(@PathVariable int doc_id) throws CsvValidationException, IOException{
		Document d = dr.findById(doc_id).get();
		ArrayList<HashMap<String,String>> ret = new ArrayList<HashMap<String,String>>(ds.getFillableVars(d));
		return ResponseEntity.status(200).body(ret);
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@PostMapping("fill")
	public ResponseEntity<HashMap<String,String>> fill(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, @RequestBody HashMap<String,Object> req) throws CsvValidationException, IOException, Exception {
		User u = util.getUserfromToken(auth);
		System.out.println(u.getAR_nom());
		HashMap<String,String> ret = new HashMap<String,String>();
		Document d = dr.findById(Integer.parseInt((String)req.get("id"))).get();
		String filename = "";
		if(!d.isNeeds_form()) {
			System.out.println("Needs form: "+d.isNeeds_form());
			filename = ds.generate_doc(u, d, ds.getDbMappings(u, d));
			System.out.println(ds.getDbMappings(u, d));
			Request r = vs.createRequest(u, d, filename);
			System.out.println(d.isRequires_approval());
			if(d.isRequires_approval()) {
				//notify user that his request has been created
				ns.makeNotif(u, u, r, nd.reqCreated(d));
				//notify the next layer that a request is waiting for him
				ns.makeNotif(u, ur.findByRole("layer3").get(), r, nd.pendingReq(r));
			}else {
				vs.completeRequest((Pending_request) r);
				//notify user that document has been saved
				ns.makeNotif(u, u, r, nd.reqSaved(d));
			}
		}
		else {
			System.out.println("Needs form: "+d.isNeeds_form());
			HashMap<String,String> mappings = new HashMap<String,String>();
			ArrayList<HashMap<String,String>> a = (ArrayList<HashMap<String, String>>) req.get("vars");
			HashMap<String,String> temp = new HashMap<String,String>();
			for(HashMap<String,String> b : a) {
				temp.put(b.get("varname"), b.get("value"));
			}
			mappings.putAll(temp);
			mappings.putAll(ds.getDbMappings(u, d));
			filename = ds.generate_doc(u ,d, mappings);
			Request r = vs.createRequest(u, d, filename);
			System.out.println(d.isRequires_approval());
			if(d.isRequires_approval()) {
				//notify user that his request has been created
				ns.makeNotif(u, u , r, nd.reqCreated(d));
				//notify the next layer that a request is waiting for him
				ns.makeNotif(u, ur.findByRole("layer3").get(), r, nd.pendingReq(r));
			}
			else {
				vs.completeRequest((Pending_request) r);
				//notify user that document has been saved
				ns.makeNotif(u, u, r, nd.reqSaved(d));
			}
		}
		ret.put("filename", filename);
		return ResponseEntity.status(200).body(ret);
	}
	
	@PreAuthorize("hasRole('User')" + "|| hasRole('layer3')" + "|| hasRole('layer2')" + "|| hasRole('layer1')")
	@GetMapping("getByID/{id}")
	public Document getbyid(@PathVariable int id) {
		return dr.findById(id).get();
	}
	
}
