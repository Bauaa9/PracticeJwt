package com.rohit.practice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="transactions")
public class ModelTransaction {

	@Id
	int trs_id;
	
	@Column
	LocalDateTime date_time;
	
	@Column
	String expenditure;

	public ModelTransaction(int trs_id, LocalDateTime date_time, String expenditure) {
		super();
		this.trs_id = trs_id;
		this.date_time = date_time;
		this.expenditure = expenditure;
	}

	public ModelTransaction() {
		super();
	}

	public int getTrs_id() {
		return trs_id;
	}

	public void setTrs_id(int trs_id) {
		this.trs_id = trs_id;
	}

	public LocalDateTime getDate_time() {
		return date_time;
	}

	public void setDate_time(LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public String getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}
	
	
}
