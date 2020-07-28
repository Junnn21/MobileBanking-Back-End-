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
	
	public CustomerDummy getCustomerDummyById(long id) {
		return repository.findCustomerDummyById(id);
	}
	
	public CustomerDummy getCustomerDummyByCifCode(String cif_code) {
		return repository.findCustomerDummyByCifCode(cif_code);
	}
	
	public CustomerDummy getCustomerDummyByPan(String pan) {
		return repository.findCustomerDummyByPan(pan);
	}
	
	public CustomerDummy saveNewCustomer(CustomerDummy customerDummy) {
		return repository.save(customerDummy);
	}
	
}
