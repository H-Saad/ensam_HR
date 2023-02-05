package com.hr.springboot.service_module.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import com.hr.springboot.userData_module.models.Role;
import com.hr.springboot.userData_module.models.Type_personnel;

@Entity
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name="DOC_ROLE",
		joinColumns = {
				@JoinColumn(name="DOC_ID")
		},
		inverseJoinColumns = {
				@JoinColumn(name="ROLE_ID")
		}
	)
	private Set<Role> allowed_roles;
	private boolean needs_form;
	private boolean requires_approval;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name="DOC_PERS",
		joinColumns = {
				@JoinColumn(name="DOC_ID")
		},
		inverseJoinColumns = {
				@JoinColumn(name="PERS_ID")
		}
	)
	private Set<Type_personnel> allowed_personnel;
	
	public Document() {
		
	}

	public Document(int id, String title, Set<Role> allowed_roles, boolean needs_form, boolean requires_approval,
			Set<Type_personnel> allowed_personnel) {
		super();
		this.id = id;
		this.title = title;
		this.allowed_roles = allowed_roles;
		this.needs_form = needs_form;
		this.requires_approval = requires_approval;
		this.allowed_personnel = allowed_personnel;
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

	public Set<Role> getAllowed_roles() {
		return allowed_roles;
	}

	public void setAllowed_roles(Set<Role> allowed_roles) {
		this.allowed_roles = allowed_roles;
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

	@Override
	public String toString() {
		return "Document [id=" + id + ", title=" + title + ", allowed_roles=" + allowed_roles + ", needs_form="
				+ needs_form + ", requires_approval=" + requires_approval + "]";
	}

	public Set<Type_personnel> getAllowed_personnel() {
		return allowed_personnel;
	}

	public void setAllowed_personnel(Set<Type_personnel> allowed_personnel) {
		this.allowed_personnel = allowed_personnel;
	}
	
}