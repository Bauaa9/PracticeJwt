package com.rohit.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.Cardlimit;


@Repository
public interface CardlimitRepo extends JpaRepository<Cardlimit, Integer> {
	
	@Query(value="select * from cardlimitdetails where card_id=?1", nativeQuery=true)
	public Cardlimit findLimit(int id);

}
