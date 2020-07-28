package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.function.Function;
import com.app.service.mobilebanking.CustomerService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@RequestMapping(value = "/saveNewCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> saveNewCustomer(@RequestBody ObjectNode object){
		List<Customer> allCustomer = service.getAllCustomer();
		String cifCode = Long.toString(Function.generateCifCode());
		Customer customer = new Customer();
		int flag = 0;
		if(allCustomer.size() != 0) {
			while(flag != 1) {
				for (int i = 0; i < allCustomer.size(); i++) {
					if(allCustomer.get(i).getCifCode().equals(cifCode)) {
						cifCode = Long.toString(Function.generateCifCode());
						flag = 0;
						break;
					}else {
						flag = 1;
					}
				}
			}
		}
		customer.setCifCode(cifCode);
		customer.setEmail(object.get("email").asText());
		customer.setFull_name(object.get("fullName").asText());
		return new ResponseEntity<>(service.saveNewCustomer(customer), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getCustomerByCifCode", method = RequestMethod.POST)
	public ResponseEntity<Customer> getCustomerByCifCode(@RequestBody ObjectNode object){
		return new ResponseEntity<>(service.findCustomerByCifCode(object.get("cif_code").asText()), HttpStatus.OK);
	}
	
	public Customer getCustomerById(long id){
		return service.findCustomerById(id);
	}
	
	public Customer saveCustomer(CustomerDummy customerDummy) {
		Customer customer = new Customer();
		customer.setCifCode(customerDummy.getCifCode());
		customer.setEmail(customerDummy.getEmail());
		customer.setFull_name(customerDummy.getFull_name());
		return  service.saveNewCustomer(customer);
	}
	
}
