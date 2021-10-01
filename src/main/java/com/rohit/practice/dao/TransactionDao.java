package com.rohit.practice.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionDao extends CrudRepository<Object, Integer> {

	@Query(value = "",nativeQuery = true)
	
}
