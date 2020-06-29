package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TargetAccount;
import com.app.repository.mobilebanking.TargetAccountRepository;

@Service
public class TargetAccountService {

	@Autowired
	private TargetAccountRepository repository;
	
	public TargetAccount saveNewTargetAccount(TargetAccount targetAccount) {
		return repository.save(targetAccount);
	}
	
	public List<TargetAccount> getTargetAccount(Customer customer){
		return repository.findByCustomer(customer);
	}
	
}
