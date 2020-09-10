package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.BillpaymentTransaction;
import com.app.repository.mobilebanking.BillpaymentTransactionRepository;

@Service
public class BillpaymentTransactionService {

	@Autowired
	private BillpaymentTransactionRepository repository;
	
	public List<BillpaymentTransaction> getAllBillpaymentTransaction(){
		return repository.findAll();
	}
	
	public BillpaymentTransaction saveNewBillpaymentTransaction(BillpaymentTransaction transaction) {
		return repository.save(transaction);
	}
	
}
