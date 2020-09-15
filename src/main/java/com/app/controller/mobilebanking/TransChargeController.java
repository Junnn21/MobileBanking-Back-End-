package com.app.controller.mobilebanking;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TransCharge;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.LookupService;
import com.app.service.mobilebanking.TransChargeService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TransChargeController {
	
	@Autowired
	private TransChargeService service;
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@RequestMapping(value = "/transCharge", method = RequestMethod.GET)
	public List<TransCharge> getAllTransCharge(){
		return service.getAllTransCharge();
	}
	
	@RequestMapping(value = "/getFundTransferTransCharge", method = RequestMethod.GET)
	public List<TransCharge> getFundTransferTransCharge(){
		return service.findTransChargeByTransactionType("fund_transfer");
	}
	
	@RequestMapping(value = "/getBillPaymentTransCharge", method = RequestMethod.POST)
	public ResponseEntity<TransCharge>  getBillPaymentTransCharge(@RequestBody ObjectNode object) {
		List<TransCharge> transChargeList = service.findTransChargeByTransactionType("bill_payment");
		TransCharge transCharge = new TransCharge();
		for (int i = 0; i < transChargeList.size(); i++) {
			if(transChargeList.get(i).getMerchant_mode().equals(object.get("merchantCode").asText())) {
				transCharge = transChargeList.get(i);
			}
		}
		
		if(transCharge.getReference_key()==null) {
			return new ResponseEntity<TransCharge>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<TransCharge>(transCharge, HttpStatus.OK );	
		}
	}
	
	@RequestMapping(value = "/getTransChargeById", method = RequestMethod.POST)
	public TransCharge getTransChargeById(@RequestBody ObjectNode object) {
		long id = object.get("transChargeId").asLong();
		return service.getTransChargeById(id);
	}
	
	@RequestMapping(value = "/saveNewTransCharge", method = RequestMethod.POST)
	public TransCharge saveNewTransCharge(@RequestBody ObjectNode object) {
		TransCharge newTransCharge = new TransCharge();
		Timestamp date = new Timestamp(System.currentTimeMillis());
		Lookup lookup = lookupService.getLookupById(object.get("relationToBank").asLong());
		List<Status> statusList = statusRepository.findByType("trans_charge");
		Status status = new Status();
		for (int i = 0; i < statusList.size(); i++) {
			if(statusList.get(i).getCode().equals("aktif")) {
				status = statusList.get(i);
			}
		}
		
		newTransCharge.setTransactionType(object.get("transactionType").asText());
		newTransCharge.setMerchant_mode(object.get("merchantMode").asText());
		newTransCharge.setRelation_to_bank(lookup);
		newTransCharge.setCharge_amount(object.get("chargeAmount").asDouble());
		newTransCharge.setEffective_date(date);
		newTransCharge.setReference_key(
				object.get("transactionType").asText() + "|" + 
				object.get("merchantMode").asText() + "|" + 
				lookup.getCode()
		);
		newTransCharge.setStatus(status);
		newTransCharge.setCurrency("IDR");
		newTransCharge.setEffective_hour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		return service.saveNewTransCharge(newTransCharge);
	}
}
