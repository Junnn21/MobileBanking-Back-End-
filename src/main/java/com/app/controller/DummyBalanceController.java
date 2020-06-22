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
import com.app.entity.DummyBalance;
import com.app.service.AccountService;
import com.app.service.DummyBalanceService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class DummyBalanceController {
	
	@Autowired
	private DummyBalanceService service;
	
	@Autowired
	private AccountService accountService;
	
	//buat show semua balance
	@RequestMapping(value = "/getAllDummyBalance", method = RequestMethod.GET)
	public List<DummyBalance> getAllDummyBalance(){
		return service.getAllDummyBalance();
	}
	
	//buat dapetin balance dengan kirim accountNumber
	@RequestMapping(value = "/findDummyBalanceByAccountNumber", method = RequestMethod.POST)
	public String findDummyBalanceByAccountNumber(@RequestBody ObjectNode accountNumber) {
		String accNumber = accountNumber.get("accountNumber").asText();
		Account account = accountService.findAccountByAccountNumber(accNumber);
		String balance = String.valueOf((int)service.findDummyBalanceByAccountNumber(account).getBalance());
		return balance;
	}
	
	//buat save DummyBalance baru, gatau buat apa siapa tau kepake
	@RequestMapping(value = "/saveNewDummyBalance", method = RequestMethod.POST)
	public ResponseEntity<String> saveNewDummyBalance(@RequestBody ObjectNode object) {
		double balance = object.get("balance").asDouble();
		Account account = accountService.findAccountByAccountNumber(object.get("accountNumber").asText());
		DummyBalance dummyBalance = new DummyBalance();
		
		dummyBalance.setBalance(balance);
		dummyBalance.setAccount(account);
	
		try {
			service.saveNewDummyBalance(dummyBalance);
			return new ResponseEntity<>("DummyBalance Created Succesfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
