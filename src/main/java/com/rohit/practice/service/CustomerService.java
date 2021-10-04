package com.rohit.practice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.rohit.practice.dao.AddressRepository;
import com.rohit.practice.dao.CarddetailsRepo;
import com.rohit.practice.dao.CardlimitRepo;
import com.rohit.practice.dao.CustomerRepo;
import com.rohit.practice.dao.ProfileRepo;
import com.rohit.practice.dao.TransactionDao;
import com.rohit.practice.model.DAOUser;
import com.rohit.practice.model.ModelAddress;
import com.rohit.practice.model.ModelCardDetails;
import com.rohit.practice.model.ModelCardlimit;
import com.rohit.practice.model.ModelCustomerLogin;
import com.rohit.practice.model.ModelProfile;
import com.rohit.practice.model.ModelTransaction;
import com.rohit.practice.model.UserDTO;

@Service
public class CustomerService {

	@Autowired
	CustomerRepo repoCustomer;

	@Autowired
	CarddetailsRepo repoCardDetails;

	@Autowired
	CardlimitRepo repoCardLimitDetails;

	@Autowired
	ProfileRepo repoProfile;

	@Autowired
	TransactionDao transactionDao;
	
	@Autowired 
	AddressRepository addressRepo;


	public Map<String, Object> creditcarddetails() {
		ModelCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
		Map<String, Object> map = new HashMap<String, Object>();
		double totaloutstanding = Double.valueOf(obj1.getTotalcreditlimit())
				- Double.valueOf(obj1.getAvailablecreditlimit());
		map.put("cardetails", obj);
		map.put("cardlimit", obj1);
		map.put("totaloutstanding", totaloutstanding);
		return map;

	}

	public Map<String, Object> getUnbilledTxn() {
		ModelCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
		String nextStatementDate = transactionDao.findNextStmtDate(obj1.getLaststatementdate());
		System.out.println(nextStatementDate);
		List<ModelTransaction> modelTransaction = transactionDao.findUnBilledTransactions(obj1.getLaststatementdate(),
				nextStatementDate);
		Map<String, Object> map = new HashMap<String, Object>();
		Float totalOutstandingAmount = transactionDao.getTotalOutstandingAmount(obj1.getLaststatementdate(),
				nextStatementDate);
		System.out.println(totalOutstandingAmount);
		map.put("unbilledTxn", modelTransaction);
		map.put("totalOutstandingAmount", totalOutstandingAmount);
		return map;
	}

	public Map<String, Object> getBilledTxn() {
		ModelCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
		System.out.println(obj1.getLaststatementdate());
		String previousStatementDate = transactionDao.findPreviousStmtDate(obj1.getLaststatementdate());
		List<ModelTransaction> modelTransaction = transactionDao.findBilledTransactions(obj1.getLaststatementdate(),
				previousStatementDate);
		Map<String, Object> map = new HashMap<String, Object>();
		Float totalAmountDue = transactionDao.getTotalAmountDue(obj1.getLaststatementdate(),
				previousStatementDate);
		map.put("billedTxn", modelTransaction);
		map.put("totalAmountDue", totalAmountDue);
		map.put("minAmountDue", (int)(totalAmountDue/9));
		return map;
	}

	public int getUserId() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		DAOUser modelCustomerLogin = repoCustomer.findUser(username);
		return (int) modelCustomerLogin.getId();
	}

	public ModelCardDetails addCard(ModelCardDetails cd) {
		cd.setCustomer_id(getUserId());
		cd.setCard_id((int) repoCardDetails.count() + 1);
		return (ModelCardDetails) repoCardDetails.save(cd);
	}
	
	public Map<String, Object> getRetailTxn() {
		ModelCardDetails obj = repoCardDetails.findCard(getUserId());
		List<ModelTransaction> modelTransaction = transactionDao.findRetailTransactions();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retailTxn", modelTransaction);
		return map;
	}

	public Map<String, Object> displayAll() {
		List<ModelCardDetails> temp = repoCardDetails.findByUserId(getUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allCards", temp);
		return map;
	}

	public void deleteCard(Integer cardId) {
		repoCardDetails.deleteById(cardId);
	}


	public Map<String, Object> getProfileById() {
		ModelProfile profile = repoProfile.getUserDetailsById(getUserId());
		Map<String, Object> map = new HashMap<>();
		map.put("profileDetails", profile);
		return map;
	}

	public Map<String, Object> updateProfileById(ModelProfile profileDetails) {
		ModelProfile profile = repoProfile.getUserDetailsById(getUserId());
		profile.setFirstName(profileDetails.getFirstName());
		profile.setLastName(profileDetails.getLastName());
		profile.setEmail(profileDetails.getEmail());
		profile.setPhoneNo(profileDetails.getPhoneNo());
		profile.setSso(profileDetails.getSso());
		ModelProfile updatedProfile = repoProfile.save(profile);
		Map<String, Object> map = new HashMap<>();
		map.put("profileDetails", updatedProfile);
		return map;
	}
	
	public Map<String, Object> getAllAddress()
	{
		List<ModelAddress> listAddresses =addressRepo.findAllAddresses(getUserId());
		Map<String, Object> map = new HashMap<>();
		map.put("addresses", listAddresses);
		return map;
	}

	public Map<String, Object> addAddress(ModelAddress address) 
	{
//		address.setAddress_id (((int)addressRepo.count())+1);
		address.setUser_id(getUserId());
		System.out.println(address.toString());
		addressRepo.save(address);
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Added address succesfully");
		return map;
	}
	
	public Map<String, Object> deleteAddress(@PathVariable int id)
	{
		ModelAddress address = addressRepo.findById(id).orElseThrow();
		addressRepo.delete(address);
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Deleted address succesfully");
		return map;
	}

}
