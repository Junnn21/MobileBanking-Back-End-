package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Customer;
import com.app.repository.mobilebanking.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	public List<Customer> getAllCustomer(){
		return repository.findAll();
	}
	
	public Customer findCustomerById(long id) {
		return repository.findById(id);
	}
	
	public Customer saveNewCustomer(Customer customer) {
		return repository.save(customer);
	}
	
	public Customer findCustomerByCifCode(String cif_code) {
		return repository.findCustomerByCifCode(cif_code);
	}
	
}
