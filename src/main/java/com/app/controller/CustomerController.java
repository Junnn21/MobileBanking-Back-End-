package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Customer;
import com.app.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<Customer> getAllCustomer(){
		return service.getAllCustomer();
	}
	
}
