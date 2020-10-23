package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TransactionRecommendation;
import com.app.service.mobilebanking.TransactionRecommendationService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TransactionRecommendationController {

	@Autowired
	private TransactionRecommendationService service;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private TargetBankController targetBankController;
	
	@Autowired
	private BillpaymentMerchantController billpaymentMerchantController;
	
	@RequestMapping(value = "/saveRecommendation", method = RequestMethod.POST)
	public void saveTransactionRecommendation(@RequestBody ObjectNode object) {
		TransactionRecommendation transactionRecommendation = new TransactionRecommendation();
		transactionRecommendation.setId(object.get("key").asText());
		transactionRecommendation.setCustomer(customerController.getByCifCode(object.get("cif_code").asText()));
		transactionRecommendation.setTarget_account_subscriber(object.get("target_account_subscriber").asText());
		transactionRecommendation.setTarget_name(object.get("target_name").asText());
		if(object.get("type").asText().equals("FUNDTRANSFER"))
			transactionRecommendation.setTarget_bank(targetBankController.getById(object.get("target_bank_merchant").asInt()));
		else
			transactionRecommendation.setTarget_merchant(billpaymentMerchantController.findByCode(object.get("target_bank_merchant").asText()));
		transactionRecommendation.setType(object.get("type").asText());
		transactionRecommendation.setCount(object.get("count").asInt());
		service.saveTransactionRecommendtion(transactionRecommendation);
	}
	
	@RequestMapping(value = "/getTransactionRecommendation", method = RequestMethod.POST)
	public List<TransactionRecommendation> getByCustomer(@RequestBody ObjectNode object){
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		return service.getTransactionRecommendationByCustomer(customer);
	}
	
}
