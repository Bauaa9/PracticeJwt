package com.rohit.practice.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.CardDetails;


@Repository
public interface CarddetailsRepo extends JpaRepository<CardDetails, Integer> {
	
	@Query(value="select * from cardetails where customer_id=?1 and card_type='credit'", nativeQuery=true)
	public CardDetails findCard(int id);

}
