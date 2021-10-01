package com.rohit.practice.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.ModelCustomerLogin;


@Repository
public interface CustomerRepo extends JpaRepository<ModelCustomerLogin,Integer> {
	
	@Query(value="select * from customerlogin where username=?1", nativeQuery=true)
	public ModelCustomerLogin findUser(String username);

}
