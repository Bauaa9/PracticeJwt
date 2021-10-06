package com.rohit.practice.service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
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
	
	    private static SecretKeySpec secretKey;
	    private static byte[] key;
	    private static final String ALGORITHM = "AES";
	    private String secretkey="slytherin";

	    public void prepareSecreteKey(String myKey) {
	        MessageDigest sha = null;
	        try {
	            key = myKey.getBytes(StandardCharsets.UTF_8);
	            sha = MessageDigest.getInstance("SHA-1");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            secretKey = new SecretKeySpec(key, ALGORITHM);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	    }

	    public String encrypt(String strToEncrypt, String secret) {
	        try {
	            prepareSecreteKey(secret);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	        } catch (Exception e) {
	            System.out.println("Error while encrypting: " + e.toString());
	        }
	        return null;
	    }

	    public String decrypt(String strToDecrypt, String secret) {
	        try {
	            prepareSecreteKey(secret);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	        } catch (Exception e) {
	            System.out.println("Error while decrypting: " + e.toString());
	        }
	        return null;
	    }


	public Map<String, Object> creditcarddetails() {
		try {
			ModelCardDetails obj = repoCardDetails.findCard(getUserId());
			ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
			obj.setCard_number(decrypt(obj.getCard_number(),secretkey));
			Map<String, Object> map = new HashMap<String, Object>();
			double totaloutstanding = Double.valueOf(obj1.getTotalcreditlimit())
					- Double.valueOf(obj1.getAvailablecreditlimit());
			map.put("cardetails", obj);
			map.put("cardlimit", obj1);
			map.put("totaloutstanding", totaloutstanding);
			return map;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NumberFormatException();
		}

	}

	public Map<String, Object> getUnbilledTxn() {
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error is "+ e);
			
		}
		return null;
	}

	public Map<String, Object> getBilledTxn() {
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error is"+ e);
		}
		return null;
	}

	public int getUserId() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		DAOUser modelCustomerLogin = repoCustomer.findUser(username);
		return (int) modelCustomerLogin.getId();
	}

	public ModelCardDetails addCard(ModelCardDetails cd) {
		try {
			cd.setCustomer_id(getUserId());
			cd.setCard_id((int) repoCardDetails.count() + 1);
			cd.setCard_number(encrypt(cd.getCard_number(),secretkey));
			System.out.println(cd.getCard_number());
			System.out.println(decrypt(cd.getCard_number(),secretkey));
			
			return (ModelCardDetails) repoCardDetails.save(cd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String, Object> getRetailTxn() {
		Map<String, Object> map;
		try {
			ModelCardDetails obj = repoCardDetails.findCard(getUserId());
			List<ModelTransaction> modelTransaction = transactionDao.findRetailTransactions();
			map = new HashMap<String, Object>();
			map.put("retailTxn", modelTransaction);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Map<String, Object> displayAll() {
		List<ModelCardDetails> temp = repoCardDetails.findAll();
		for(int i=0;i<temp.size();i++)
		{
			ModelCardDetails modelCard = temp.get(i);
			modelCard.setCard_number(decrypt(modelCard.getCard_number(),secretkey));
		}
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
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		try {
			address.setUser_id(getUserId());
			System.out.println(address.toString());
			addressRepo.save(address);
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Added address succesfully");
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error is: "+ e);
		}
		return null;
	}
	
	public Map<String, Object> deleteAddress(@PathVariable int id)
	{
		try {
			ModelAddress address = addressRepo.findById(id).orElseThrow();
			addressRepo.delete(address);
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Deleted address succesfully");
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error is: "+ e);
		}
		return null;
	}

}