package com.app.controller.corebankingdummy;

import java.util.ArrayList;
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
import com.app.entity.mobilebanking.BillpaymentTransaction;
import com.app.entity.mobilebanking.FundTransfer;
import com.app.repository.corebankingdummy.AccountDummyRepository;
import com.app.responseBody.AccountStatementResponse;
import com.app.service.corebankingdummy.AccountStatementDummyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class AccountStatementDummyController {

	@Autowired
	private AccountStatementDummyService service;
	
	@Autowired
	private AccountDummyController accountDummyController;
	
	public ResponseEntity<AccountStatementDummy> saveAccountStatementDummy(
			@RequestBody AccountDummy account,FundTransfer fundTransfer, String type, String detail, Double amount, Double balance
	){
		AccountStatementDummy accountStatement = new AccountStatementDummy();	
		Date postingDate = new Date();
		accountStatement.setAccount(account);
		accountStatement.setAmount(amount);
		accountStatement.setBalance_after_transaction(balance);
		accountStatement.setCurrency(fundTransfer.getCurrency());
		accountStatement.setCustomer_note(fundTransfer.getMessage());;
		accountStatement.setDetail(detail);
		accountStatement.setPosting_date(postingDate);
		accountStatement.setTransaction_reference_number(fundTransfer.getTransaction_reference_number());;
		accountStatement.setTransaction_type(type);
		return new ResponseEntity<>(service.saveAccountStatementDummy(accountStatement), HttpStatus.OK);
	}
	
	public AccountStatementDummy saveBillPaymentAccountStatementDummy(String accNumber, BillpaymentTransaction billPayment, String type, String detail, Double amount) {
		AccountStatementDummy accountStatement = new AccountStatementDummy();
		Date postingDate = new Date();
		accountStatement.setAccount(accountDummyController.findAccountDummyByAccountNumber(accNumber));
		accountStatement.setAmount(amount);
		accountStatement.setBalance_after_transaction(accountDummyController.getBalance(accNumber));
		accountStatement.setCurrency(billPayment.getCurrency());
		accountStatement.setCustomer_note("-");
		accountStatement.setDetail(detail);
		accountStatement.setPosting_date(postingDate);
		accountStatement.setTransaction_reference_number(billPayment.getTransaction_reference_number());
		accountStatement.setTransaction_type(type);
		return service.saveAccountStatementDummy(accountStatement);
	}
	
//	@RequestMapping(value = "/accountStatement", method = RequestMethod.POST)
//	public ResponseEntity<List<AccountStatementDummy>> getAccountStatementByAccount(@RequestBody ObjectNode object){
//		return new ResponseEntity<>(service.getAccountStatementDummyByAccount(accountDummyController.findAccountDummyById(object.get("account").asLong())), HttpStatus.OK);
//	}
	
//	@RequestMapping(value = "/getAccountStatementByAccountNumber", method = RequestMethod.POST)
//	public ResponseEntity<List<AccountStatementDummy>> getAccountStatementByAccountNumber(@RequestBody ObjectNode object){
//		AccountDummy acountDummy = accountDummyRepo.findAccountDummyByAccountNumber(object.get("accountNumber").asText());
//		return new ResponseEntity<>(service.getAccountStatementDummyByAccount(acountDummy), HttpStatus.OK);
//	}
	
	
	public ArrayList<AccountStatementDummy> getAccountStatementByAccountNumber(@RequestBody String accountNumber){
		ArrayList<AccountStatementDummy> statementList = new ArrayList<AccountStatementDummy>();
		AccountDummy accountDummy = accountDummyController.findAccountDummyByAccountNumber(accountNumber);
		List<AccountStatementDummy> accountStatement = service.getAccountStatementDummyByAccount(accountDummy);
		for (int i = 0; i < accountStatement.size(); i++) {
			statementList.add(accountStatement.get(i));
		}
		return statementList;
	}
	
	public ArrayList<AccountStatementResponse> getAllAccountStatementDummy (@RequestBody ArrayList<String> accountNumberList){
		ArrayList<AccountStatementResponse> statementList = new ArrayList<AccountStatementResponse>();
		for (int i = 0; i < accountNumberList.size(); i++) {
			AccountStatementResponse statement = new AccountStatementResponse();
			statement.setAccountNumber(accountNumberList.get(i));
			statement.setStatements(getAccountStatementByAccountNumber(accountNumberList.get(i)));
			statementList.add(statement);
		}
		return statementList;
	}
	
}
