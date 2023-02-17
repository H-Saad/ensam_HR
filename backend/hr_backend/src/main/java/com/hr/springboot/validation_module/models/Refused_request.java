package com.hr.springboot.validation_module.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Refused_request implements Request{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private int document_id;
	private String doc_title;
	private LocalDateTime datetime;
	private boolean approved_by_l3;
	private boolean approved_by_l2;
	private boolean approved_by_l1;
	private LocalDateTime l3_datetime;
	private LocalDateTime l2_datetime;
	private LocalDateTime l1_datetime;
	private int l3_id;
	private int l2_id;
	private int l1_id;
	private LocalDateTime refusal_datetime;
	private String refusal_cause;
	private int refused_by;
	private String user_file;
	private String l3_file;
	private String l2_file;
	private String l1_file;
	
	public Refused_request() {
		
	}

	public Refused_request(int id, int user_id, int document_id, String doc_title, LocalDateTime datetime,
			boolean approved_by_l3, boolean approved_by_l2, boolean approved_by_l1, LocalDateTime l3_datetime,
			LocalDateTime l2_datetime, LocalDateTime l1_datetime, int l3_id, int l2_id, int l1_id,
			LocalDateTime refusal_datetime, String refusal_cause, int refused_by, String user_file, String l3_file,
			String l2_file, String l1_file) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.document_id = document_id;
		this.doc_title = doc_title;
		this.datetime = datetime;
		this.approved_by_l3 = approved_by_l3;
		this.approved_by_l2 = approved_by_l2;
		this.approved_by_l1 = approved_by_l1;
		this.l3_datetime = l3_datetime;
		this.l2_datetime = l2_datetime;
		this.l1_datetime = l1_datetime;
		this.l3_id = l3_id;
		this.l2_id = l2_id;
		this.l1_id = l1_id;
		this.refusal_datetime = refusal_datetime;
		this.refusal_cause = refusal_cause;
		this.refused_by = refused_by;
		this.user_file = user_file;
		this.l3_file = l3_file;
		this.l2_file = l2_file;
		this.l1_file = l1_file;
	}

	public Refused_request(Pending_request p) {
		this.user_id = p.getUser_id();
		this.document_id = p.getDocument_id();
		this.datetime = p.getDatetime();
		this.approved_by_l3 = p.isApproved_by_l3();
		this.approved_by_l2 = p.isApproved_by_l2();
		this.approved_by_l1 = p.isApproved_by_l1();
		this.l3_datetime = p.getL3_datetime();
		this.l2_datetime = p.getL2_datetime();
		this.l1_datetime = p.getL1_datetime();
		this.l3_id = p.getL3_id();
		this.l2_id = p.getL2_id();
		this.l1_id = p.getL1_id();
		this.l1_file = p.getL1_file();
		this.l2_file = p.getL2_file();
		this.l3_file = p.getL3_file();
		this.user_file = p.getUser_file();
		this.doc_title = p.getDoc_title();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getDocument_id() {
		return document_id;
	}

	public void setDocument_id(int document_id) {
		this.document_id = document_id;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public boolean isApproved_by_l3() {
		return approved_by_l3;
	}

	public void setApproved_by_l3(boolean approved_by_l3) {
		this.approved_by_l3 = approved_by_l3;
	}

	public boolean isApproved_by_l2() {
		return approved_by_l2;
	}

	public void setApproved_by_l2(boolean approved_by_l2) {
		this.approved_by_l2 = approved_by_l2;
	}

	public boolean isApproved_by_l1() {
		return approved_by_l1;
	}

	public void setApproved_by_l1(boolean approved_by_l1) {
		this.approved_by_l1 = approved_by_l1;
	}

	public LocalDateTime getL3_datetime() {
		return l3_datetime;
	}

	public void setL3_datetime(LocalDateTime l3_datetime) {
		this.l3_datetime = l3_datetime;
	}

	public LocalDateTime getL2_datetime() {
		return l2_datetime;
	}

	public void setL2_datetime(LocalDateTime l2_datetime) {
		this.l2_datetime = l2_datetime;
	}

	public LocalDateTime getL1_datetime() {
		return l1_datetime;
	}

	public void setL1_datetime(LocalDateTime l1_datetime) {
		this.l1_datetime = l1_datetime;
	}

	public int getL3_id() {
		return l3_id;
	}

	public void setL3_id(int l3_id) {
		this.l3_id = l3_id;
	}

	public int getL2_id() {
		return l2_id;
	}

	public void setL2_id(int l2_id) {
		this.l2_id = l2_id;
	}

	public int getL1_id() {
		return l1_id;
	}

	public void setL1_id(int l1_id) {
		this.l1_id = l1_id;
	}

	public LocalDateTime getRefusal_datetime() {
		return refusal_datetime;
	}

	public void setRefusal_datetime(LocalDateTime refusal_datetime) {
		this.refusal_datetime = refusal_datetime;
	}

	public String getRefusal_cause() {
		return refusal_cause;
	}

	public void setRefusal_cause(String refusal_cause) {
		this.refusal_cause = refusal_cause;
	}

	public int getRefused_by() {
		return refused_by;
	}

	public void setRefused_by(int refused_by) {
		this.refused_by = refused_by;
	}

	public String getUser_file() {
		return user_file;
	}

	public void setUser_file(String user_file) {
		this.user_file = user_file;
	}

	public String getL3_file() {
		return l3_file;
	}

	public void setL3_file(String l3_file) {
		this.l3_file = l3_file;
	}

	public String getL2_file() {
		return l2_file;
	}

	public void setL2_file(String l2_file) {
		this.l2_file = l2_file;
	}

	public String getL1_file() {
		return l1_file;
	}

	public void setL1_file(String l1_file) {
		this.l1_file = l1_file;
	}

	@Override
	public String getType() {
		return "refused";
	}

	public String getDoc_title() {
		return doc_title;
	}

	public void setDoc_title(String doc_title) {
		this.doc_title = doc_title;
	}
}
