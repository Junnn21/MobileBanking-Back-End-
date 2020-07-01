package com.app.controller.mobilebanking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.app.controller.corebankingdummy.AccountDummyController;
import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.mobilebanking.Account;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.repository.corebankingdummy.AccountDummyRepository;
import com.app.repository.corebankingdummy.CustomerDummyRepository;
import com.app.repository.mobilebanking.CustomerRepository;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.responseBody.AccountBalanceResponse;
import com.app.service.mobilebanking.AccountService;


@RestController
public class AccountController {

	@Autowired
	private AccountService service;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AccountDummyController accountDummyController;
	
	
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
	public Account findAccountByAccountNumber(@RequestBody String accountNumber) {	
		return service.findAccountByAccountNumber(accountNumber);
	}

	//buat bikin arrayList dari rekening, buat dapetin balance
	public ArrayList<String> createAccountNumberList(List<Account> accountList){
		ArrayList<String> accountNumberList = new ArrayList<String>();
		for(int i = 0; i < accountList.size(); i++) {
			accountNumberList.add(accountList.get(i).getAccountNumber());
		}
		return accountNumberList;
	}
	
	
	//buat link-unlink account channel sama corebanking
	@RequestMapping(value = "/linkUnlinkAccount", method = RequestMethod.POST)
	public ArrayList<AccountBalanceResponse> linkUnlinkAccount(@RequestBody ObjectNode object){
		
		List<AccountDummy> coreBankingAccountList = accountDummyController.findAccountDummyByCustomerDummy(object);
		List<Account> channelAccountList = service.getListAccountByCustomer(customerRepo.findById(object.get("customer").asLong()));
		System.out.println("rekening channel: ");
		for(int i = 0; i < channelAccountList.size(); i++) {
			System.out.println(channelAccountList.get(i).getAccountNumber());
		}
		
		//Langsung masukin semua rekening (berdasarkan customer) dari corebanking kalau list rekening (berdasarkan customer) di channel kosong
		if(channelAccountList.size() == 0) {
			for(int i = 0; i < coreBankingAccountList.size(); i++) {
				Account account = new Account();
				account.setAccountNumber(coreBankingAccountList.get(i).getAccountNumber());
				account.setAccount_name(coreBankingAccountList.get(i).getAccount_name());
				account.setCustomer(customerRepo.findById(coreBankingAccountList.get(i).getCustomer().getId()));
				account.setStatus(statusRepo.findById(coreBankingAccountList.get(i).getStatus().getId()));
				service.saveNewAccount(account);
			}
			
		//ini kalau list rekening (berdasarkan customer) di channel ga kosong	
		}else {
			
			for(int i = 0; i < channelAccountList.size(); i++) {
				//ngecek semua rekening di channel, kedaftar apa engga di corebanking, kalau gada apus dari channel
				if(accountDummyController.findAccountDummyByAccountNumber(channelAccountList.get(i).getAccountNumber()) == null) {
					service.deleteAccount(channelAccountList.get(i).getAccountNumber());
				}
			}
			
			for(int i = 0; i < coreBankingAccountList.size(); i++) {
				//nyari tiap rekening di core banking ada apa engga di channel, kalau null (ga ketemu) langsung masukkin ke channel
				if(service.findAccountByAccountNumber(coreBankingAccountList.get(i).getAccountNumber()) == null) {
					Account account = new Account();
					account.setAccountNumber(coreBankingAccountList.get(i).getAccountNumber());
					account.setAccount_name(coreBankingAccountList.get(i).getAccount_name());
					account.setCustomer(customerRepo.findById(coreBankingAccountList.get(i).getCustomer().getId()));
					account.setStatus(statusRepo.findById(coreBankingAccountList.get(i).getStatus().getId()));
					service.saveNewAccount(account);
				}
			}
		}
		return accountDummyController.getAllBalanceByAccountNumber(createAccountNumberList(service.getListAccountByCustomer(customerRepo.findById(object.get("customer").asLong()))));
	}
}	