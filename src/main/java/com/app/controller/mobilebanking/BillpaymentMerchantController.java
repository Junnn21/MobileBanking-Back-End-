package com.app.controller.mobilebanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.Status;
import com.app.service.mobilebanking.BillpaymentMerchantService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class BillpaymentMerchantController {

	@Autowired
	private BillpaymentMerchantService service;
	
	@Autowired
	private StatusController statusController;
	
	@RequestMapping(value = "/saveNewBillpaymentMerchant", method = RequestMethod.POST)
	public ResponseEntity<BillpaymentMerchant> saveNewBillpaymentMerchant (@RequestBody ObjectNode object){
		Status status = statusController.findStatus("billpayment_merchant", "aktif");
		BillpaymentMerchant billpaymentMerchant = new BillpaymentMerchant();
		billpaymentMerchant.setCurrency("IDR");
		billpaymentMerchant.setCode(object.get("code").asText());
		billpaymentMerchant.setDescription(object.get("description").asText());
		billpaymentMerchant.setName(object.get("name").asText());
		billpaymentMerchant.setStatus(status);
		
		return new ResponseEntity<>(service.saveNewBillpaymentMerchant(billpaymentMerchant), HttpStatus.OK);
	}
	
	public BillpaymentMerchant findByCode(String code) {
		return service.findByCode(code);
	}
	
}
