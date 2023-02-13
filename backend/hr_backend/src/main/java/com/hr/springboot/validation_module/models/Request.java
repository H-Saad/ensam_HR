package com.hr.springboot.validation_module.models;

import java.time.LocalDateTime;

public interface Request {
	public int getId();

	public void setId(int id);

	public int getUser_id();

	public void setUser_id(int user_id);

	public int getDocument_id();

	public void setDocument_id(int document_id);

	public LocalDateTime getDatetime();

	public void setDatetime(LocalDateTime datetime);

	public boolean isApproved_by_l3();

	public void setApproved_by_l3(boolean approved_by_l3);

	public boolean isApproved_by_l2();

	public void setApproved_by_l2(boolean approved_by_l2);

	public boolean isApproved_by_l1();

	public void setApproved_by_l1(boolean approved_by_l1);

	public LocalDateTime getL3_datetime();

	public void setL3_datetime(LocalDateTime l3_datetime);

	public LocalDateTime getL2_datetime();

	public void setL2_datetime(LocalDateTime l2_datetime);

	public LocalDateTime getL1_datetime();

	public void setL1_datetime(LocalDateTime l1_datetime);

	public int getL3_id();

	public void setL3_id(int l3_id);

	public int getL2_id();
	
	public void setL2_id(int l2_id);

	public int getL1_id();

	public void setL1_id(int l1_id);

	public String getType();
	
	public String getDoc_title();

	public void setDoc_title(String doc_title);
}
