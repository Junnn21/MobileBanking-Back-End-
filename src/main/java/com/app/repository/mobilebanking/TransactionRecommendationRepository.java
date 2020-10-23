package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TransactionRecommendation;

@Repository
public interface TransactionRecommendationRepository extends JpaRepository<TransactionRecommendation, String>{
	List<TransactionRecommendation> findFirst5ByCustomerOrderByCountDesc(Customer customer);
}
