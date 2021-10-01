package com.rohit.practice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.practice.model.UserDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HelloWorldController {

	@RequestMapping("/hello")
	public String firstPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
String username = userDetails.getUsername();
System.out.println(userDetails.toString());
		return "Hello World";
	}
	
	
	@PostMapping("/unbilled-transactions")
	public ResponseEntity<?> getUnbilledTrans(){
		return ResponseEntity.of(null);
	}

}