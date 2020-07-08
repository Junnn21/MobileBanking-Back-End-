package com.app.controller.mobilebanking;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TransCharge;
import com.app.function.Function;
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
		Status status = statusRepository.findById(object.get("status").asLong());
		String cif = String.valueOf((int)Function.generateCifCode());
		
		newTransCharge.setTransaction_type(object.get("transactionType").asText());
		newTransCharge.setMerchant_mode(object.get("merchantMode").asText());
		newTransCharge.setRelation_to_bank(lookup);
		newTransCharge.setCif_code(cif);
		newTransCharge.setCharge_amount(object.get("chargeAmount").asDouble());
		newTransCharge.setEffective_date(date);
		newTransCharge.setReference_key(
				object.get("transactionType").asText() + "|" + 
				object.get("merchantMode").asText() + "|" + 
				lookup.getCode() + "|" + 
				cif
		);
		newTransCharge.setStatus(status);
		newTransCharge.setCurrency("IDR");
		newTransCharge.setEffective_hour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		return service.saveNewTransCharge(newTransCharge);
	}
}
