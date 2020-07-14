package com.app.controller.corebankingdummy;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.corebankingdummy.AccountStatementDummy;
import com.app.entity.mobilebanking.FundTransfer;
import com.app.repository.corebankingdummy.AccountDummyRepository;
import com.app.service.corebankingdummy.AccountStatementDummyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class AccountStatementDummyController {

	@Autowired
	private AccountStatementDummyService service;
	
	@Autowired
	private AccountDummyRepository accountDummyRepo;
	
	public ResponseEntity<AccountStatementDummy> saveAccountStatementDummy(
			@RequestBody AccountDummy account,FundTransfer fundTransfer, String type, String detail, Double amount, Double balance
	){
		AccountStatementDummy accountStatement = new AccountStatementDummy();
		String referenceNumber = fundTransfer.getTransaction_reference_number();
		String accountStatementReferenceNumber = referenceNumber.substring(referenceNumber.length() - 10);
		Date postingDate = new Date();
		accountStatement.setAccount(account);
		accountStatement.setAmount(amount);
		accountStatement.setBalance_after_transaction(balance);
		accountStatement.setCurrency(fundTransfer.getCurrency());
		accountStatement.setCustomer_note(fundTransfer.getMessage());
		accountStatement.setDetail(detail);
		accountStatement.setPosting_date(postingDate);
		accountStatement.setTransaction_reference_number(accountStatementReferenceNumber);
		accountStatement.setTransaction_type(type);
		return new ResponseEntity<>(service.saveAccountStatementDummy(accountStatement), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accountStatement", method = RequestMethod.POST)
	public ResponseEntity<List<AccountStatementDummy>> getAccountStatementByAccount(@RequestBody ObjectNode object){
		return new ResponseEntity<>(service.getAccountStatementDummyByAccount(accountDummyRepo.findAccountDummyById(object.get("account").asLong())), HttpStatus.OK);
	}
	
}