package com.rohit.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.ModelProfile;

@Repository
public interface ProfileRepo extends JpaRepository<ModelProfile,Integer> {
	
	@Query(value = "select customer_detail_id,first_name,last_name,email,phone_no,sso from customerdetails where cust_id=?1",nativeQuery = true)
	public ModelProfile getUserDetailsById(int id);
}
