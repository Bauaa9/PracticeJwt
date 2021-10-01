package com.rohit.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.Cardlimit;
import com.rohit.practice.model.ModelTransaction;

@Repository
public interface TransactionDao extends JpaRepository<ModelTransaction, Integer>{

	@Query(value="select * from cardlimitdetails where card_id=?1", nativeQuery=true)
	public Cardlimit findLimit(int id);
}
