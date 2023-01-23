package com.hr.springboot.service_module.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hr.springboot.userData_module.models.Role;

@Entity
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String template;
	private Set<Role> allowed_roles;
	private Vars vars;
	private boolean needs_form;
	private boolean requires_approval;
	
	public Document() {
		
	}

	public Document(int id, String title, String template, Set<Role> allowed_roles, Vars vars, boolean needs_form,
			boolean requires_approval) {
		super();
		this.id = id;
		this.title = title;
		this.template = template;
		this.allowed_roles = allowed_roles;
		this.vars = vars;
		this.needs_form = needs_form;
		this.requires_approval = requires_approval;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Set<Role> getAllowed_roles() {
		return allowed_roles;
	}

	public void setAllowed_roles(Set<Role> allowed_roles) {
		this.allowed_roles = allowed_roles;
	}

	public Vars getVars() {
		return vars;
	}

	public void setVars(Vars vars) {
		this.vars = vars;
	}

	public boolean isNeeds_form() {
		return needs_form;
	}

	public void setNeeds_form(boolean needs_form) {
		this.needs_form = needs_form;
	}

	public boolean isRequires_approval() {
		return requires_approval;
	}

	public void setRequires_approval(boolean requires_approval) {
		this.requires_approval = requires_approval;
	}
	
}