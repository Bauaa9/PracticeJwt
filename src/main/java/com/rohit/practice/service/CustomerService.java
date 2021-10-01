package com.rohit.practice.service;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.practice.dao.CarddetailsRepo;
import com.rohit.practice.dao.CardlimitRepo;
import com.rohit.practice.dao.CustomerRepo;
import com.rohit.practice.model.CardDetails;
import com.rohit.practice.model.Cardlimit;
import com.rohit.practice.model.CustomerLogin;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepo repository;
	
	@Autowired
	CarddetailsRepo repo1;
	
	@Autowired
	CardlimitRepo repo2;
	
	public CustomerLogin getUser(String username) {
		return this.repository.findUser(username);
	}
	
	public String verify(CustomerLogin user)
	{
try {
			CustomerLogin checkUser = getUser(user.getUsername());
			
			if (checkUser.getPassword().equals(user.getPassword()))
				return "true";
			else
				return "false";
		} catch (Exception e) {
			throw new NoSuchElementException();
		}
	}
	
	public Map<String,Object> creditcarddetails(int id)
	{
		CardDetails obj=repo1.findCard(id);
		Cardlimit obj1=repo2.findLimit(obj.getCard_id());
		System.out.println(obj.getCard_holder_name());
		System.out.println(obj1.getAvailablecashlimit());
		Map<String,Object> map=new HashMap<String,Object>();
		double totaloutstanding=Double.valueOf(obj1.getTotalcreditlimit())-Double.valueOf(obj1.getAvailablecreditlimit());
		map.put("cardetails", obj);
		map.put("cardlimit", obj1);
		map.put("totaloutstanding", totaloutstanding);
		
		return map;
		
	}

}
