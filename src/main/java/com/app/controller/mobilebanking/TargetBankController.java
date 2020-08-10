package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.TargetBank;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.TargetBankService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TargetBankController {

	@Autowired
	private TargetBankService service;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@RequestMapping(value = "/saveNewTargetBank", method = RequestMethod.POST)
	public ResponseEntity<TargetBank> saveNewTargetBank(@RequestBody ObjectNode object){
		TargetBank targetBank = new TargetBank();
		targetBank.setBank_name(object.get("bank_name").asText());
		targetBank.setNetwork_code(object.get("network_code").asText());
		targetBank.setNetwork_enabled(object.get("network_enabled").asText());
		targetBank.setRtgs_code(object.get("rtgs_code").asText());
		targetBank.setRtgs_enabled(object.get("rtgs_enabled").asText());
		targetBank.setSknCode(object.get("skn_code").asText());
		targetBank.setSkn_enabled(object.get("skn_enabled").asText());
		targetBank.setStatus(statusRepo.findById(object.get("status").asLong()));
		return new ResponseEntity<>(service.saveNewTargetBank(targetBank), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/targetBank", method = RequestMethod.POST)
	public List<TargetBank> getAllTargetBank(){
		return service.getAllTargetBank();
	}
	
	@RequestMapping(value = "/getTargetBankBySknCode", method = RequestMethod.POST)
	public TargetBank getTargetBankBySknCode(@RequestBody ObjectNode object) {
		return service.getTargetBankBySknCode(object.get("bankCode").asText());
	}
	
}
