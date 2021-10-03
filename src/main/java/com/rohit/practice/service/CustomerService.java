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

import com.rohit.practice.dao.CarddetailsRepo;
import com.rohit.practice.dao.CardlimitRepo;
import com.rohit.practice.dao.CustomerRepo;
import com.rohit.practice.dao.ProfileRepo;
import com.rohit.practice.dao.TransactionDao;
import com.rohit.practice.model.ModelCardDetails;
import com.rohit.practice.model.ModelCardlimit;
import com.rohit.practice.model.ModelCustomerLogin;
import com.rohit.practice.model.ModelProfile;
import com.rohit.practice.model.ModelTransaction;

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

	public ModelCustomerLogin getUser(String username) {
		return this.repoCustomer.findUser(username);
	}

	public String verify(ModelCustomerLogin user) {
		try {
			ModelCustomerLogin checkUser = getUser(user.getUsername());

			if (checkUser.getPassword().equals(user.getPassword()))
				return "true";
			else
				return "false";
		} catch (Exception e) {
			throw new NoSuchElementException();
		}
	}

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
		ModelCustomerLogin modelCustomerLogin = repoCustomer.findUser(username);
		return modelCustomerLogin.getCustomer_id();
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
		List<ModelCardDetails> temp = repoCardDetails.findAll();
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

}
