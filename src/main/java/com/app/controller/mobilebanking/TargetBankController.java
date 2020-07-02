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
		targetBank.setBank_name(object.get("bankName").asText());
		targetBank.setNetwork_code(object.get("networkCode").asInt());
		targetBank.setNetwork_enabled(object.get("networkEnabled").asInt());
		targetBank.setRtgs_code(object.get("rtgsCode").asInt());
		targetBank.setRtgs_enabled(object.get("rtgsEnabled").asInt());
		targetBank.setSkn_code(object.get("sknCode").asInt());
		targetBank.setSkn_enabled(object.get("sknEnabled").asInt());
		targetBank.setStatus(statusRepo.findById(object.get("status").asLong()));
		return new ResponseEntity<>(service.saveNewTargetBank(targetBank), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/targetBank", method = RequestMethod.GET)
	public List<TargetBank> getAllTargetBank(){
		return service.getAllTargetBank();
	}
	
}
