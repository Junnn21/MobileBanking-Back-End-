package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.corebankingdummy.BillpaymentAccountDummyController;
import com.app.entity.corebankingdummy.BillpaymentAccountDummy;
import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TargetSubscriber;
import com.app.service.mobilebanking.TargetSubscriberService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TargetSubscriberController {

	@Autowired
	private TargetSubscriberService service;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private BillpaymentAccountDummyController billpaymentAccountDummyController;
	
	@Autowired
	private StatusController statusController;
	
	@Autowired
	private BillpaymentMerchantController billpaymentMerchantController;
	
	@RequestMapping(value = "/saveNewTargetSubscriber", method = RequestMethod.POST)
	public ResponseEntity<TargetSubscriber> saveNewTargetSubscriber(@RequestBody ObjectNode object){
		
		BillpaymentMerchant merchant = billpaymentMerchantController.findByCode(object.get("merchantCode").asText());
		BillpaymentAccountDummy billpaymentAccountDummy = billpaymentAccountDummyController.findBillpaymentAccountDummy(object.get("subscriberNumber").asText(), merchant);
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		Status status = statusController.findStatus("target_subscriber", "aktif");
		
		TargetSubscriber exist = service.getTargetSubscriberBySubscriberNumberAndMerchant(object.get("subscriberNumber").asText(), merchant);
		if(exist != null) {
			exist.setStatus(status);
			return new ResponseEntity<>(service.saveNewTargetSubscriber(exist), HttpStatus.OK);
		}else {
			TargetSubscriber targetSubscriber = new TargetSubscriber();
			targetSubscriber.setSubscriberNumber(billpaymentAccountDummy.getAccountNumber());
			targetSubscriber.setMerchant_detail(merchant);
			targetSubscriber.setCurrency("IDR");
			targetSubscriber.setCustomer(customer);
			targetSubscriber.setName(billpaymentAccountDummy.getName());
			targetSubscriber.setStatus(status);
			return new ResponseEntity<>(service.saveNewTargetSubscriber(targetSubscriber), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/getTargetSubscriber", method = RequestMethod.POST)
	public ResponseEntity<List<TargetSubscriber>> getTargetSubscriber(@RequestBody ObjectNode object){
		String keyword = object.get("keyword").asText();
		Status status = statusController.findStatus("target_subscriber", "aktif");
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		List<TargetSubscriber> activeTargetSubscriber = service.getTargetSubscriber(customer, status);
		if(keyword.isEmpty()) {
			return new ResponseEntity<>(activeTargetSubscriber, HttpStatus.OK);
		}else {
			if(Character.isDigit(keyword.charAt(0))) {
				return new ResponseEntity<>(service.getTargetSubscriberSubscriberNumberSearched(customer, status, keyword), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(service.getTargetSubscriberNameSearched(customer, status, keyword), HttpStatus.OK);
			}
		}
	}
	
	@RequestMapping(value = "/getTargetSubscriberByMerchant", method = RequestMethod.POST)
	public ResponseEntity<List<TargetSubscriber>> getTargetSubscriberByMerchant(@RequestBody ObjectNode object){
		String keyword = object.get("keyword").asText();
		Status status = statusController.findStatus("target_subscriber", "aktif");
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		BillpaymentMerchant merchant = billpaymentMerchantController.findByCode(object.get("merchantCode").asText());
		List<TargetSubscriber> activeTargetSubscriberByMerchant = service.getTargetSubscriberByMerhant(customer, merchant, status);
		if(keyword.isEmpty()) {
			return new ResponseEntity<>(activeTargetSubscriberByMerchant, HttpStatus.OK);
		}else {
			if(Character.isDigit(keyword.charAt(0))) {
				return new ResponseEntity<>(service.getTargetSubscriberByMerchantSubscriberNumberSearched(customer, merchant, status, keyword), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(service.getTargetSubscriberByMerchantNameSearched(customer, merchant, status, keyword), HttpStatus.OK);
			}
		}
	}
	
	@RequestMapping(value = "/deleteTargetSubscriber", method = RequestMethod.POST)
	public ResponseEntity<TargetSubscriber> deleteTargetSubscriber(@RequestBody ObjectNode object){
		TargetSubscriber targetSubscriber = service.findById(object.get("targetSubscriber").asLong());
		Status newStatus = statusController.findStatus("target_subscriber", "inaktif");
		targetSubscriber.setStatus(newStatus);
		
		return new ResponseEntity<>(service.saveNewTargetSubscriber(targetSubscriber), HttpStatus.OK);
	}
	
}
