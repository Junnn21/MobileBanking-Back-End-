package com.app.service.corebankingdummy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.repository.corebankingdummy.CustomerDummyRepository;

@Service
public class CustomerDummyService {

	@Autowired
	private CustomerDummyRepository repository;
	
	public List<CustomerDummy> getAllCustomerDummy(){
		return repository.findAll();
	}
	
	public CustomerDummy saveNewCustomer(CustomerDummy customerDummy) {
		return repository.save(customerDummy);
	}
	
}
