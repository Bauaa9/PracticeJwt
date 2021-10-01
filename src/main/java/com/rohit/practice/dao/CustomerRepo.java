package com.rohit.practice.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.CustomerLogin;


@Repository
public interface CustomerRepo extends JpaRepository<CustomerLogin,Integer> {
	
	@Query(value="select * from customerlogin where username=?1", nativeQuery=true)
	public CustomerLogin findUser(String username);

}
