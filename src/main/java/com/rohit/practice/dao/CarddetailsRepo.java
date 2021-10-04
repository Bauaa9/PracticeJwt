package com.rohit.practice.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.practice.model.ModelCardDetails;


@Repository
public interface CarddetailsRepo extends JpaRepository<ModelCardDetails, Integer> {
	
	@Query(value="select * from cardetails where customer_id=?1 and card_type='credit' limit 1", nativeQuery=true)
	public ModelCardDetails findCard(int id);
	
	@Query(value="select * from cardetails where customer_id=?1 and card_type='credit'", nativeQuery=true)
	public List<ModelCardDetails> findByUserId(int id);

}
