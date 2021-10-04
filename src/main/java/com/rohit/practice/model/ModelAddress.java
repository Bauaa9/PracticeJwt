package com.rohit.practice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="address")
public class ModelAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int address_id;
	@Column(name="user_id")
	int user_id;
	@Column(name="line1")
	String line1;
	@Column(name="line2")
	String line2;
	@Column(name="pincode")
	String pincode;
	@Column(name="city")
	String city;
	
	public ModelAddress() {
		// TODO Auto-generated constructor stub
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getUser_id() {
		return user_id;
	}

	@Override
	public String toString() {
		return "ModelAddress [address_id=" + address_id + ", user_id=" + user_id + ", line1=" + line1 + ", line2="
				+ line2 + ", pincode=" + pincode + ", city=" + city + "]";
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ModelAddress(int address_id, int user_id, String line1, String line2, String pincode, String city) {
		super();
		this.address_id = address_id;
		this.user_id = user_id;
		this.line1 = line1;
		this.line2 = line2;
		this.pincode = pincode;
		this.city = city;
	}

	
}
