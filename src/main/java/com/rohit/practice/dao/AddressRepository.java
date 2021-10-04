package com.rohit.practice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.ModelAddress;
import com.rohit.practice.model.ModelTransaction;

@Repository
public interface AddressRepository extends JpaRepository<ModelAddress, Integer> {

	@Query(value="select * from address where user_id=?1", nativeQuery=true)
	public List<ModelAddress> findAllAddresses(int id);
}
