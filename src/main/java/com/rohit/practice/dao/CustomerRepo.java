package com.rohit.practice.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.DAOUser;
import com.rohit.practice.model.ModelCustomerLogin;
import com.rohit.practice.model.UserDTO;


@Repository
public interface CustomerRepo extends JpaRepository<DAOUser,Integer> {
	
	@Query(value="select * from validation where username=?1", nativeQuery=true)
	public DAOUser findUser(String username);

}
