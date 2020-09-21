package com.app.controller.mobilebanking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.controller.corebankingdummy.AccountStatementDummyController;
import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.BillpaymentTransaction;
import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.Status;
import com.app.function.Function;
import com.app.service.mobilebanking.BillpaymentTransactionService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class BillpaymentTransactionController {
	
	@Autowired
	private BillpaymentTransactionService service;
	
	@Autowired
	private StatusController statusController;
	
	@Autowired
	private LookupController lookupController;
	
	@Autowired
	private BillpaymentMerchantController merchantController;
	
	@Autowired
	private AccountController accountController;
	
	@Autowired
	private AccountStatementDummyController accountStatementDummyController;
	
	@RequestMapping(value = "/getAllBillpaymentTransaction", method = RequestMethod.GET)
	public ResponseEntity<List<BillpaymentTransaction>> getAllBillpaymentTransaction(){
		return new ResponseEntity<List<BillpaymentTransaction>>(service.getAllBillpaymentTransaction(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveNewBillpaymentTransaction" , method = RequestMethod.POST)
	public ResponseEntity<BillpaymentTransaction> saveNewBillpaymentTransaction(@RequestBody ObjectNode object){
		BillpaymentTransaction newTransaction = new BillpaymentTransaction();
		BillpaymentMerchant merchant = merchantController.findByCode(object.get("merchantCode").asText()); //dapetin merchantnya siapa
		Double amount = object.get("amount").asDouble();
		Double bankCharge = object.get("bankCharge").asDouble();
		Double totalAmountDebited = amount + bankCharge;
		Status status = statusController.findStatus("bill_payment", "sukses");
		Date transactionDate = new Date();
		
		//tentuin transaction_type
		Lookup transaction_type = new Lookup();
		if(merchant.getCode().equals("5374") || merchant.getCode().equals("2289")) {
			transaction_type = lookupController.findLookUpByTypeAndCode("bill_payment", "topup");
		}else if(merchant.getCode().equals("4992")) {
			transaction_type = lookupController.findLookUpByTypeAndCode("bill_payment", "purchase");
		}
		
		newTransaction.setTransaction_type(transaction_type);
		newTransaction.setMerchant(merchant);
		newTransaction.setTransaction_reference_number(Function.generateTransactionReferenceNumber("MBP"));
		newTransaction.setDebit_account(accountController.findAccountByAccountNumber(object.get("accNumber").asText()));
		newTransaction.setCustomer_number(object.get("customerNumber").asText());
		newTransaction.setCustomer_name(object.get("customerName").asText());
		newTransaction.setCurrency("IDR");
		newTransaction.setPayment_amount(amount);
		newTransaction.setBank_charge(bankCharge);
		newTransaction.setTotal_amount_debited(totalAmountDebited);
		newTransaction.setStatus(status);
		newTransaction.setTransaction_date(transactionDate);
		newTransaction.setBank_reference_number(Function.generateTransactionReferenceNumber("BP"));
		
		//update balance
		accountController.updateBalanceCore(object.get("accNumber").asText(), -totalAmountDebited);
		
		//create statement
		accountStatementDummyController.saveBillPaymentAccountStatementDummy(object.get("accNumber").asText(), newTransaction, "Bill Payment", object.get("customerNumber").asText(), -totalAmountDebited);
		
		return new ResponseEntity<>(service.saveNewBillpaymentTransaction(newTransaction), HttpStatus.OK);
	}
	
	
}
