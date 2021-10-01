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
import com.rohit.practice.dao.TransactionDao;
import com.rohit.practice.model.CardDetails;
import com.rohit.practice.model.Cardlimit;
import com.rohit.practice.model.CustomerLogin;
import com.rohit.practice.model.ModelTransaction;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepo repository;
	
	@Autowired
	CarddetailsRepo repoCardDetails;
	
	@Autowired
	CardlimitRepo repoCardLimitDetails;
	
	@Autowired
	TransactionDao transactionDao;
	
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
		CardDetails obj=repoCardDetails.findCard(id);
		Cardlimit obj1=repoCardLimitDetails.findLimit(obj.getCard_id());
		System.out.println(obj.getCard_holder_name());
		System.out.println(obj1.getAvailablecashlimit());
		Map<String,Object> map=new HashMap<String,Object>();
		double totaloutstanding=Double.valueOf(obj1.getTotalcreditlimit())-Double.valueOf(obj1.getAvailablecreditlimit());
		map.put("cardetails", obj);
		map.put("cardlimit", obj1);
		map.put("totaloutstanding", totaloutstanding);
		
		return map;
		
	}

	
	public Map<String,Object> getUnbilledTxn(int id)
	{
		CardDetails obj=repoCardDetails.findCard(id);
		Cardlimit obj1=repoCardLimitDetails.findLimit(obj.getCard_id());
		System.out.println(obj1.getLaststatementdate());
		String previousStatementDate = transactionDao.findPreviousStmtDate(obj1.getLaststatementdate());
		String nextStatementDate = transactionDao.findNextStmtDate(obj1.getLaststatementdate());
		ModelTransaction modelTransaction = transactionDao.findBilledTransactions(obj1.getLaststatementdate(),previousStatementDate,nextStatementDate);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("unbilledTxn", modelTransaction );		
		return map;
	}
	
	public Map<String,Object> getBilledTxn(int id)
	{
		CardDetails obj=repoCardDetails.findCard(id);
		Cardlimit obj1=repoCardLimitDetails.findLimit(obj.getCard_id());
		System.out.println(obj1.getLaststatementdate());
		String previousStatementDate = transactionDao.findPreviousStmtDate(obj1.getLaststatementdate());
		String nextStatementDate = transactionDao.findNextStmtDate(obj1.getLaststatementdate());
		ModelTransaction modelTransaction = transactionDao.findBilledTransactions(obj1.getLaststatementdate(),previousStatementDate,nextStatementDate);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("billedTxn", modelTransaction );		
		return map;
	}
	
}
