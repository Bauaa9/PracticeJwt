package com.rohit.practice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.practice.model.UserDTO;
import com.rohit.practice.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
	
	@Autowired
	CustomerService service;

	@PostMapping("/creditdetails")
	public ResponseEntity<?> cardinfo()
	{
		Map<String,Object> map=service.creditcarddetails();
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/unbilled-transactions")
	public ResponseEntity<?> getUnbilledTrans(){
		Map<String,Object> map = service.getUnbilledTxn();
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/billed-transactions")
	public ResponseEntity<?> getBilledTrans(){
		Map<String,Object> map = service.getBilledTxn();
		return ResponseEntity.ok(map);
	}

}