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
import org.springframework.web.client.RestTemplate;

import com.app.controller.corebankingdummy.AccountDummyController;
import com.app.controller.corebankingdummy.AccountStatementDummyController;
import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.FundTransfer;
import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TargetAccount;
import com.app.function.Function;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.FundTransferService;
import com.app.service.mobilebanking.LookupService;
import com.app.service.mobilebanking.TargetBankService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		Double bankCharge = object.get("bankCharge").asDouble();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		Status status = statusRepository.findByTypeAndCode("fund_transfer", "sukses");
		
		List<Lookup> lookupList = lookupService.getLookupByType("fund_transfer");
		Lookup transaction_type = new Lookup();
		for (int i = 0; i < lookupList.size(); i++) {
			if(targetBankService.getTargetBankById(object.get("targetBankId").asLong()).getSknCode().equals("153")) {
				if(lookupList.get(i).getCode().equals("inbank")) {
					transaction_type = lookupList.get(i);
				}
			}else if(bankCharge == 0){
				if(lookupList.get(i).getCode().equals("skn")) {
					transaction_type = lookupList.get(i);
				}
			}else {
				if(lookupList.get(i).getCode().equals("network")) {
					transaction_type = lookupList.get(i);
				}
			}
		}
		
		newFundTransfer.setTransaction_reference_number(Function.generateTransactionReferenceNumber("MB"));
		newFundTransfer.setAccount(accountController.findAccountByAccountNumber(accountNumber));
		newFundTransfer.setAmount(amount);
		newFundTransfer.setBank_charge(bankCharge);
		newFundTransfer.setMessage(object.get("message").asText() != null ? object.get("message").asText() : "-");
		newFundTransfer.setStatus(status);
		newFundTransfer.setTarget_account(targetAccount);
		newFundTransfer.setTarget_bank(targetBankService.getTargetBankById(object.get("targetBankId").asLong()));
		newFundTransfer.setTotal_amount_debited(amount + bankCharge);
		newFundTransfer.setTransaction_type(transaction_type);
		newFundTransfer.setTransfer_date(time);
		newFundTransfer.setCurrency(targetAccount.getCurrency());
		newFundTransfer.setBank_reference_number(Function.generateTransactionReferenceNumber("FT"));
		
		//proses update balance dari rekening pengirim
		accountController.updateBalanceCore(accountNumber, -newFundTransfer.getTotal_amount_debited());
		
		//create new account statement rekening pengirim
		Double balancePengirim = accountDummyController.getBalance(accountNumber);
		accountStatementController.saveAccountStatementDummy(
				accountDummyPengirim, newFundTransfer, "Fund Transfer", targetAccountNumber + " - " + accountDummyController.findAccountDummyByAccountNumber(targetAccountNumber).getAccount_name(), 
				-newFundTransfer.getTotal_amount_debited(), balancePengirim
		);
		
		//proses update balance dari rekening penerima
		accountController.updateBalanceCore(targetAccountNumber, newFundTransfer.getAmount());
		
		//create new account statement rekening penerima
		Double balancePenerima = accountDummyController.getBalance(targetAccountNumber);
		accountStatementController.saveAccountStatementDummy(
				accountDummyPenerima, newFundTransfer, "Fund Transfer", accountNumber + " - " + accountDummyController.findAccountDummyByAccountNumber(accountNumber).getAccount_name(), 
				newFundTransfer.getAmount(), balancePenerima
		);
		
		ObjectMapper mapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		ObjectNode kafkaObject = mapper.createObjectNode();
		kafkaObject.put("cif_code", accountDummyPengirim.getCustomer().getCifCode());
		kafkaObject.put("target_account_subscriber", targetAccount.getAccount_number());
		kafkaObject.put("target_bank_merchant", targetAccount.getBank_detail().getId());
		kafkaObject.put("type", "FUNDTRANSFER");
		restTemplate.postForLocation("http://localhost:8181/produceTransaction", kafkaObject);
		
		return new ResponseEntity<FundTransfer>(service.saveNewFundTransfer(newFundTransfer), HttpStatus.OK);
	}
}
