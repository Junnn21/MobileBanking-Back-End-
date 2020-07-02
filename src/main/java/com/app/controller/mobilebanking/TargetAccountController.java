package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TargetAccount;
import com.app.entity.mobilebanking.TargetBank;
import com.app.repository.mobilebanking.CustomerRepository;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.repository.mobilebanking.TargetBankRepository;
import com.app.service.mobilebanking.TargetAccountService;
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
	
	//tambah target account
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
	
	//munculin list target account 
	@RequestMapping(value = "/getTargetAccounts", method = RequestMethod.POST)
	public ResponseEntity<List<TargetAccount>> getTargetAccount(@RequestBody ObjectNode object){
		return new ResponseEntity<List<TargetAccount>>(service.getTargetAccount(customerRepo.findById(object.get("customer").asLong())), HttpStatus.OK);
	}
	
}
