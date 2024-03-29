package com.hr.springboot.service_module.models;

/**
 * @author Speedlight
 *
 */
public class Var {
	private String varname;
	private String vartype;
	private String description;
	private boolean needs_form;
	
	public Var() {
		
	}

	public Var(String varname, String vartype, String description, boolean needs_form) {
		super();
		this.varname = varname;
		this.vartype = vartype;
		this.description = description;
		this.needs_form = needs_form;
	}

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

	public String getVartype() {
		return vartype;
	}

	public void setVartype(String vartype) {
		this.vartype = vartype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNeeds_form() {
		return needs_form;
	}

	public void setNeeds_form(boolean needs_form) {
		this.needs_form = needs_form;
	}

	@Override
	public String toString() {
		return "Var [varname=" + varname + ", vartype=" + vartype + ", description=" + description + ", needs_form="
				+ needs_form + "]";
	}
	
}
