package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Customer;
import com.app.entity.Status;
import com.app.entity.TargetAccount;
import com.app.entity.TargetBank;
import com.app.repository.CustomerRepository;
import com.app.repository.StatusRepository;
import com.app.repository.TargetBankRepository;
import com.app.service.TargetAccountService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TargetAccountController {

	@Autowired
	private TargetAccountService service;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private TargetBankRepository targetBankRepo;
	
	@RequestMapping(value = "/saveNewTargetAccount", method = RequestMethod.POST)
	public ResponseEntity<TargetAccount> saveNewTargetAccount(@RequestBody ObjectNode object){
		Customer customer = customerRepo.findById(object.get("customer").asLong());
		Status status = statusRepo.findById(object.get("status").asLong());
		TargetBank targetBank = targetBankRepo.findById(object.get("targetBank").asLong());
		TargetAccount targetAccount = new TargetAccount();
		targetAccount.setAccount_number(object.get("accountNumber").asText());
		targetAccount.setBank_detail(targetBank);
		targetAccount.setCurrency("IDR");
		targetAccount.setCustomer(customer);
		targetAccount.setName(customer.getFull_name());
		targetAccount.setStatus(status);
		return new ResponseEntity<>(service.saveNewTargetAccount(targetAccount), HttpStatus.OK);
	}
	
	@RequestMapping
	public ResponseEntity<List<TargetAccount>> getTargetAccount(@RequestBody ObjectNode object){
		return new ResponseEntity<List<TargetAccount>>(service.getTargetAccount(customerRepo.findById(object.get("customer").asLong())), HttpStatus.OK);
	}
	
}