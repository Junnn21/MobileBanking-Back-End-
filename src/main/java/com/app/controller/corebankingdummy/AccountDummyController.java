package com.app.controller.corebankingdummy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Status;
import com.app.repository.corebankingdummy.CustomerDummyRepository;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.corebankingdummy.AccountDummyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class AccountDummyController {

	@Autowired
	private AccountDummyService service;
	
	@Autowired
	private CustomerDummyRepository customerDummyRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@RequestMapping(value = "/accountDummy", method = RequestMethod.GET)
	public ResponseEntity<List<AccountDummy>> getAllAccountDummy(){
		return new ResponseEntity<>(service.getAllAccountDummy(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveAccountDummy", method = RequestMethod.POST)
	public ResponseEntity<AccountDummy> saveNewAccountDummy(@RequestBody ObjectNode object){
		AccountDummy account = new AccountDummy();
		CustomerDummy customer = customerDummyRepo.findCustomerDummyById(object.get("customer").asLong());
		Status status = statusRepo.findById(object.get("status").asLong());
		account.setAccount_name(object.get("account_name").asText());
		account.setAccount_number(object.get("account_number").asText());
		account.setBalance(object.get("balance").asDouble());
		account.setCustomer(customer);
		account.setStatus(status);
		return new ResponseEntity<>(service.saveNewAccountDummy(account), HttpStatus.OK);
	}
	
}
