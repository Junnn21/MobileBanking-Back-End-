package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Account;
import com.app.entity.Customer;
import com.app.entity.Status;
import com.app.service.AccountService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.app.repository.CustomerRepository;
import com.app.repository.StatusRepository;


@RestController
public class AccountController {

	@Autowired
	private AccountService service;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	//buat show semua rekening
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public List<Account> getAllAccount(){
		return service.getAllAccount();
	}
	
	//buat daftarin rekening baru
	@RequestMapping(value = "/saveNewAccount", method = RequestMethod.POST)
	public ResponseEntity<String> saveNewAccount(@RequestBody ObjectNode object) {
		System.out.println(object.get("status"));
		Status status = statusRepo.findById(object.get("status").asLong());
		Customer customer = customerRepo.findById(object.get("customer").asLong());
		System.out.println(status + " ||||| " + customer);
		if(status!=null && customer!=null) {
			
			Account newAccount = new Account();
			newAccount.setAccount_name(object.get("account_name").asText());
			newAccount.setAccountNumber(object.get("account_number").asText());
			newAccount.setStatus(status);
			newAccount.setCustomer(customer);
			
			try {
				service.saveNewAccount(newAccount);
				return new ResponseEntity<>("Account Created Succesfully!", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		else {
			return new ResponseEntity<>("Error in creating account!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//buat nyari detail rekening dengan nomor rekening
	@RequestMapping(value = "/findAccountByAccountNumber", method = RequestMethod.POST )
	public Account findAccountByAccountNumber(@RequestBody ObjectNode accountNumber) {
		System.out.println(accountNumber.get("accountNumber").asText());		
		return service.findAccountByAccountNumber(accountNumber.get("accountNumber").asText());
	}
	
}	
