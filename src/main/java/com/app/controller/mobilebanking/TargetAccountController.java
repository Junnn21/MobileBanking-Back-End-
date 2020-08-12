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

import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TargetAccount;
import com.app.entity.mobilebanking.TargetBank;
import com.app.repository.mobilebanking.CustomerRepository;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.repository.mobilebanking.TargetBankRepository;
import com.app.service.corebankingdummy.AccountDummyService;
import com.app.service.mobilebanking.TargetAccountService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TargetAccountController {

	@Autowired
	private TargetAccountService service;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AccountDummyService accountDummyService;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private TargetBankRepository targetBankRepo;
	
	//tambah target account
	@RequestMapping(value = "/saveNewTargetAccount", method = RequestMethod.POST)
	public ResponseEntity<TargetAccount> saveNewTargetAccount(@RequestBody ObjectNode object){
		AccountDummy account = accountDummyService.findAccountDummyByAccountNumber(object.get("accountNumber").asText());
		Customer customer = customerRepo.findCustomerByCifCode(object.get("cif_code").asText());
		Status status = statusRepo.findById(object.get("status").asLong());
		TargetBank targetBank = targetBankRepo.findBySknCode(object.get("bankCode").asText());
		TargetAccount targetAccount = new TargetAccount();
		targetAccount.setAccount_number(object.get("accountNumber").asText());
		targetAccount.setBank_detail(targetBank);
		targetAccount.setCurrency("IDR");
		targetAccount.setCustomer(customer);
		targetAccount.setName(account.getAccount_name());
		targetAccount.setStatus(status);
		return new ResponseEntity<>(service.saveNewTargetAccount(targetAccount), HttpStatus.OK);
	}
	
	//munculin list target account 
	@RequestMapping(value = "/getTargetAccounts", method = RequestMethod.POST)
	public ResponseEntity<List<TargetAccount>> getTargetAccount(@RequestBody ObjectNode object){
		String keyword = object.get("keyword").asText();
		Customer customer = customerRepo.findCustomerByCifCode(object.get("cif_code").asText()); 
		List<TargetAccount> allTargetAccount = service.getTargetAccount(customer);
		List<TargetAccount> activeTargetAccount = new ArrayList<>();
		List<TargetAccount> searchTargetAccount = new ArrayList<>();
		for (int i = 0; i < allTargetAccount.size(); i++) {
			if(allTargetAccount.get(i).getStatus().getCode().equals("aktif")) {
				activeTargetAccount.add(allTargetAccount.get(i));
			}
		}
		if(keyword.isEmpty()){
			return new ResponseEntity<List<TargetAccount>>(activeTargetAccount, HttpStatus.OK);
		}else {
			for (int i = 0; i < activeTargetAccount.size(); i++) {
				if(activeTargetAccount.get(i).getAccount_number().contains(keyword)) {
					searchTargetAccount.add(activeTargetAccount.get(i));
				}
			}
			return new ResponseEntity<List<TargetAccount>>(searchTargetAccount, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/deleteTargetAccount", method = RequestMethod.POST)
	public ResponseEntity<TargetAccount> deleteTargetAccount(@RequestBody ObjectNode object){
		TargetAccount targetAccount = service.findById(object.get("targetAccount").asLong());
		List<Status> status = statusRepo.findByType("target_account");
		Status newStatus = new Status();
		for (int i = 0; i < status.size(); i++) {
			if(status.get(i).getCode().equals("inaktif")) {
				newStatus = status.get(i);
				break;
			}
		}
		targetAccount.setStatus(newStatus);
		return new ResponseEntity<>(service.saveNewTargetAccount(targetAccount), HttpStatus.OK);
	}
	
	public TargetAccount getTargetAccountByCustomerAndTargetAccountNumber(Customer customer, String targetAccountNumber) {
		List<TargetAccount> targetAccountList = service.getTargetAccount(customer);
		for(int i = 0; i < targetAccountList.size(); i++) {
			if(targetAccountList.get(i).getAccount_number().equals(targetAccountNumber)) {
				return targetAccountList.get(i);
			}
		}
		return null;
	}
	
}
