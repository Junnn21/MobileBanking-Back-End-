package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TransactionRecommendation;
import com.app.repository.mobilebanking.TransactionRecommendationRepository;

@Service
public class TransactionRecommendationService {

	@Autowired
	TransactionRecommendationRepository repository;
	
	public void saveTransactionRecommendtion(TransactionRecommendation transactionRecommendation) {
		repository.save(transactionRecommendation);
	}
	
	public List<TransactionRecommendation> getTransactionRecommendationByCustomer(Customer customer){
		return repository.getByCustomer(customer);
	}
	
}
