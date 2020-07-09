package com.app.controller.mobilebanking;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.corebankingdummy.AccountDummyController;
import com.app.controller.corebankingdummy.AccountStatementDummyController;
import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.FundTransfer;
import com.app.entity.mobilebanking.TargetAccount;
import com.app.function.Function;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.FundTransferService;
import com.app.service.mobilebanking.LookupService;
import com.app.service.mobilebanking.TargetBankService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class FundTransferController {
	
	@Autowired
	private FundTransferService service;
	
	@Autowired
	private AccountController accountController;
	
	@Autowired
	private AccountStatementDummyController accountStatementController;
	
	@Autowired
	private AccountDummyController accountDummyController;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private TargetAccountController targetAccountController;
	
	@Autowired
	private TargetBankService targetBankService;
	
	@Autowired
	private LookupService lookupService;

	@RequestMapping(value = "/fundTransfer", method = RequestMethod.GET)
	public List<FundTransfer> getAllFundTransfer(){
		return service.getAllFundTransfer();
	}
	
	//buat transfer baru (BELOM NAMBAHIN STATEMENT DI CORE)
	@RequestMapping(value = "/saveNewFundTransfer", method = RequestMethod.POST)
	public ResponseEntity<FundTransfer> saveNewFundTransfer(@RequestBody ObjectNode object) {
		FundTransfer newFundTransfer = new FundTransfer();
		String accountNumber = object.get("accountNumber").asText();
		String targetAccountNumber = object.get("targetAccountNumber").asText();
		AccountDummy accountDummyPengirim = accountDummyController.getAccountDummyByAccountNumber(accountNumber);
		AccountDummy accountDummyPenerima = accountDummyController.getAccountDummyByAccountNumber(targetAccountNumber);
		Customer customer = accountController.findAccountByAccountNumber(accountNumber).getCustomer();
		TargetAccount targetAccount = targetAccountController.getTargetAccountByCustomerAndTargetAccountNumber(customer, targetAccountNumber);
		Double amount = object.get("amount").asDouble();
		Double bankCharge = (double) 0;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		//tentuin biaya transfer (asumsi target bank no 1 = sinarmas)
		if(object.get("targetBank").asInt() != 1) {
			bankCharge += 5000;		//masih hardcode, gatau dapetnya darimana
		}
		
		//proses save data transaksi
		newFundTransfer.setTransaction_reference_number(Function.generateTransactionReferenceNumber());
		newFundTransfer.setAccount(accountController.findAccountByAccountNumber(accountNumber));
		newFundTransfer.setAmount(amount);
		newFundTransfer.setBank_charge(bankCharge);
		newFundTransfer.setMessage(object.get("message").asText() != null ? object.get("message").asText() : "-");
		newFundTransfer.setStatus(statusRepository.findById(object.get("status").asLong()));
		newFundTransfer.setTarget_account(targetAccount);
		newFundTransfer.setTarget_bank(targetBankService.getTargetBankById(object.get("targetBank").asLong()));
		newFundTransfer.setTotal_amount_debited(amount + bankCharge);
		newFundTransfer.setTransaction_type(lookupService.getLookupById(object.get("lookup").asLong()));
		newFundTransfer.setTransfer_date(time);
		newFundTransfer.setCurrency(targetAccount.getCurrency());
		
		//proses update balance dari rekening pengirim
		accountController.updateBalanceCore(accountNumber, -newFundTransfer.getTotal_amount_debited());
		
		//create new account statement rekening pengirim
		Double balancePengirim = accountDummyController.getBalance(accountNumber);
		accountStatementController.saveAccountStatementDummy(
				accountDummyPengirim, newFundTransfer, "Fund Transfer", "To " + targetAccountNumber, 
				-newFundTransfer.getTotal_amount_debited(), balancePengirim
		);
		
		//proses update balance dari rekening penerima
		accountController.updateBalanceCore(targetAccountNumber, newFundTransfer.getAmount());
		
		//create new account statement rekening penerima
		Double balancePenerima = accountDummyController.getBalance(targetAccountNumber);
		accountStatementController.saveAccountStatementDummy(
				accountDummyPenerima, newFundTransfer, "Fund Transfer", "From " + accountNumber, 
				newFundTransfer.getAmount(), balancePenerima
		);
		
		return new ResponseEntity<FundTransfer>(service.saveNewFundTransfer(newFundTransfer), HttpStatus.OK);
	}
}
