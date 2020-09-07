package com.app.controller.corebankingdummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.mobilebanking.BillpaymentMerchantController;
import com.app.controller.mobilebanking.StatusController;
import com.app.entity.corebankingdummy.BillpaymentAccountDummy;
import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.Status;
import com.app.service.corebankingdummy.BillpaymentAccountDummyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class BillpaymentAccountDummyController {

	@Autowired
	private BillpaymentAccountDummyService service;
	
	@Autowired
	private StatusController statusController;
	
	@Autowired
	private BillpaymentMerchantController billpaymentMerchantController;
	
	@RequestMapping(value = "/saveNewBillpaymentAccount", method = RequestMethod.POST)
	public ResponseEntity<BillpaymentAccountDummy> saveNewBillpaymentAccount (@RequestBody ObjectNode object){
		Status status = statusController.findStatus("billpayment_account", "aktif");
		BillpaymentMerchant billpaymentMerchant = billpaymentMerchantController.findByCode(object.get("code").asText());
		BillpaymentAccountDummy billpaymentAccount = new BillpaymentAccountDummy();
		billpaymentAccount.setMerchant(billpaymentMerchant);
		billpaymentAccount.setAccount_number(object.get("account_number").asText());
		billpaymentAccount.setName(object.get("name").asText());
		billpaymentAccount.setBilled_amount(object.get("billed_amount").asDouble());
		billpaymentAccount.setStatus(status);
		
		return new ResponseEntity<>(service.saveNewBillpaymentAccountDummy(billpaymentAccount), HttpStatus.OK);
	}
	
}
