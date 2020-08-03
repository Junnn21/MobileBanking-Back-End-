package com.app.controller.corebankingdummy;

import java.util.ArrayList;
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
import com.app.function.Function;
import com.app.repository.corebankingdummy.CustomerDummyRepository;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.corebankingdummy.AccountDummyService;
import com.app.responseBody.*;
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
	public List<AccountDummy> getAllAccountDummy(){
		return service.getAllAccountDummy();
	}
	
	@RequestMapping(value = "/saveAccountDummy", method = RequestMethod.POST)
	public ResponseEntity<AccountDummy> saveNewAccountDummy(@RequestBody ObjectNode object){
		List<AccountDummy> allAccount = service.getAllAccountDummy();
		String accountNumber = Long.toString(Function.generateAccountNumber());
		int flag = 0;
		if(allAccount.size() != 0) {
			while(flag !=1) {
				for (int i = 0; i < allAccount.size(); i++) {
					if(allAccount.get(i).getAccountNumber().equals(accountNumber)) {
						accountNumber = Long.toString(Function.generateAccountNumber());
						flag = 0;
						break;
					}else {
						flag = 1;
					}
				}
			}
		}
		AccountDummy account = new AccountDummy();
		CustomerDummy customer = customerDummyRepo.findCustomerDummyById(object.get("customer").asLong());
		Status status = statusRepo.findById(object.get("status").asLong());
		account.setAccount_name(customer.getFull_name());
		account.setAccountNumber(accountNumber);
		account.setBalance(object.get("balance").asDouble());
		account.setCustomer(customer);
		account.setStatus(status);
		return new ResponseEntity<>(service.saveNewAccountDummy(account), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findAccountDummyByCustomerDummy", method = RequestMethod.POST)
	public List<AccountDummy> findAccountDummyByCustomerDummy (@RequestBody ObjectNode object){
		CustomerDummy customer = customerDummyRepo.findCustomerDummyByCifCode(object.get("cif_code").asText());
		return service.findAccountDummyByCustomer(customer);
	}
	
	public AccountDummy findAccountDummyByAccountNumber(@RequestBody String accountNumber) {
		return service.findAccountDummyByAccountNumber(accountNumber);
	}
	
	//buat dapetin semua rekening sama balance sekaligus
	public ArrayList<AccountBalanceResponse> getAllBalanceByAccountNumber(@RequestBody ArrayList<String> accountNumberList){
		ArrayList<AccountBalanceResponse> accountBalanceList = new ArrayList<AccountBalanceResponse>();
		for(int i=0; i < accountNumberList.size(); i++) {
			AccountBalanceResponse newBalance = new AccountBalanceResponse();
			newBalance.setAccountNumber(accountNumberList.get(i));
			newBalance.setBalance(String.valueOf(findAccountDummyByAccountNumber(accountNumberList.get(i)).getBalance().intValue()));
			accountBalanceList.add(newBalance);
		}
		return accountBalanceList;
	}
	
	//buat update balance kalo transaksi
	public ResponseEntity<String> updateBalance(@RequestBody String accountNumber, double amount){
		AccountDummy accountDummy = service.findAccountDummyByAccountNumber(accountNumber);
		accountDummy.setBalance(accountDummy.getBalance() + amount);  
		service.saveNewAccountDummy(accountDummy);
		return new ResponseEntity<>("Balance Updated!", HttpStatus.OK);
	}
	
	public Double getBalance(@RequestBody String accountNumber) {
		AccountDummy accountDummy = service.findAccountDummyByAccountNumber(accountNumber);
		return accountDummy.getBalance();
	}
	
	public AccountDummy getAccountDummyByAccountNumber(@RequestBody String accountNumber) {
		return service.findAccountDummyByAccountNumber(accountNumber);
	}
	
}
