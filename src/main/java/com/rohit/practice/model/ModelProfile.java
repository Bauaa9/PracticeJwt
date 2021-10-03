package com.rohit.practice.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Id;
	
@Entity
@Table(name="customerdetails")
public class ModelProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int customer_detail_id;
	@Column(name="first_name")
	String firstName;
	@Column(name="last_name")
	String lastName;
	@Column(name="email")
	String email;
	@Column(name="phone_no")
	long phoneNo;;
	@Column(name="sso")
	String sso;
	public ModelProfile() {
		// TODO Auto-generated constructor stub
	}
		
 
 public ModelProfile(int id, String firstName, String lastName, String email, long phoneNo, String sso) {
		super();
		this.customer_detail_id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.sso = sso;
	}


public long getId() {
	return customer_detail_id;
}


public void setId(int id) {
	this.customer_detail_id = id;
}


public String getFirstName() {
	return firstName;
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String lastName) {
	this.lastName = lastName;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public long getPhoneNo() {
	return phoneNo;
}


public void setPhoneNo(long phoneNo) {
	this.phoneNo = phoneNo;
}


public String getSso() {
	return sso;
}


public void setSso(String sso) {
	this.sso = sso;
}
 
}
