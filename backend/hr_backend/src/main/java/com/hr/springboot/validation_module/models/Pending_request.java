package com.hr.springboot.validation_module.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pending_request implements Request{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private int document_id;
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

	public Pending_request() {
		this.approved_by_l3 = false;
		this.approved_by_l2 = false;
		this.approved_by_l1 = false;
	}

	public Pending_request(int id, int user_id, int document_id, LocalDateTime datetime, boolean approved_by_l3,
			boolean approved_by_l2, boolean approved_by_l1, LocalDateTime l3_datetime, LocalDateTime l2_datetime,
			LocalDateTime l1_datetime, int l3_id, int l2_id, int l1_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.document_id = document_id;
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

}
