package com.rohit.practice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.practice.model.ModelAddress;
import com.rohit.practice.model.ModelCardDetails;
import com.rohit.practice.model.ModelProfile;
import com.rohit.practice.model.UserDTO;
import com.rohit.practice.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	CustomerService service;

	@PostMapping("/creditdetails")
	public ResponseEntity<?> cardinfo() {
		Map<String, Object> map = service.creditcarddetails();
		return ResponseEntity.ok(map);
	}

	@PostMapping("/unbilled-transactions")
	public ResponseEntity<?> getUnbilledTrans() {
		Map<String, Object> map = service.getUnbilledTxn();
		return ResponseEntity.ok(map);
	}

	@PostMapping("/billed-transactions")
	public ResponseEntity<?> getBilledTrans() {
		Map<String, Object> map = service.getBilledTxn();
		return ResponseEntity.ok(map);
	}

	@PostMapping("/retail-transactions")
	public ResponseEntity<?> getRetailTransactions() {
		Map<String, Object> map = service.getRetailTxn();
		return ResponseEntity.ok(map);
	}
	
	@PostMapping(value = "/new-card")
	public ResponseEntity<?> addCard(@RequestBody ModelCardDetails newCard) {
		return ResponseEntity.ok(service.addCard(newCard));
	}

	@PostMapping(value = "/display-cards")
	public ResponseEntity<?> displayAll() {
		Map<String, Object> map = service.displayAll();
		return ResponseEntity.ok(map);
	}

	@DeleteMapping(value = "/delete-card/{id}")
	public ResponseEntity<?> deleteCard(@PathVariable("id") int cardId) {
		System.out.println(cardId);
		try {
			service.deleteCard(cardId);
			return ResponseEntity.ok("Card deleted successfully!!!");
		} catch (Exception e) {
			System.err.println("## EXCEPTION: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/get-profile")
	public ResponseEntity<?> getProfileById() {
		return ResponseEntity.ok(service.getProfileById());
	}

	@PutMapping("/update-profile")
	public ResponseEntity<?> updateProfile(@RequestBody ModelProfile profile) {
		return ResponseEntity.ok(service.updateProfileById(profile));
	}
	
	@PostMapping(value = "/add-address")
	public ResponseEntity<?> addAddress(@RequestBody ModelAddress modelAddress) {
		return ResponseEntity.ok(service.addAddress(modelAddress));
	}
	
	@PostMapping("/addresses")
	public ResponseEntity<?>  getAllAddress()
	{
		return ResponseEntity.ok(service.getAllAddress());
	}
	
	@DeleteMapping("/delete/address/{id}")
	public ResponseEntity<?> deleteAddress(@PathVariable int id)
	{
		return ResponseEntity.ok(service.deleteAddress(id));
	}
	

}