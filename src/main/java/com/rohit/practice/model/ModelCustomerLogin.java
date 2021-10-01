package com.rohit.practice.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="customerlogin")
public class ModelCustomerLogin {
	@Id
	@Column(name="customer_id")
	int customer_id;
	@Column(name="username")
	String username;
	@Column(name="passowrd")
	String password;
	
	public ModelCustomerLogin()
	{
		
	}
	public ModelCustomerLogin(int customer_id, String username, String password) {
		super();
		this.customer_id = customer_id;
		this.username = username;
		this.password = password;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	

}
