package com.app.controller.mobilebanking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Status;
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
		
		List<Status> statusList = statusRepo.findByType("target_bank");
		Status status = new Status();
		for (int i = 0; i < statusList.size(); i++) {
			if(statusList.get(i).getCode().equals("aktif")) {
				status = statusList.get(i);
			}
		}
		
		TargetBank targetBank = new TargetBank();
		targetBank.setBank_name(object.get("bank_name").asText());
		targetBank.setNetwork_code(object.get("network_code").asText());
		targetBank.setNetwork_enabled(object.get("network_enabled").asText());
		targetBank.setRtgs_code(object.get("rtgs_code").asText());
		targetBank.setRtgs_enabled(object.get("rtgs_enabled").asText());
		targetBank.setSknCode(object.get("skn_code").asText());
		targetBank.setSkn_enabled(object.get("skn_enabled").asText());
		targetBank.setStatus(status);
		return new ResponseEntity<>(service.saveNewTargetBank(targetBank), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/targetBank", method = RequestMethod.POST)
	public List<TargetBank> getAllTargetBank(@RequestBody ObjectNode object){
		List<TargetBank> allTargetBank = service.getAllTargetBank();
		List<TargetBank> searchTargetBank = new ArrayList<TargetBank>();
		String keyword = object.get("keyword").asText().toUpperCase();
		if(object.get("keyword").asText().isEmpty()) {
			System.out.println("Masuk 1");
			return allTargetBank;
		}else {
			if(Character.isDigit(keyword.charAt(0))) {
				for (int i = 0; i < allTargetBank.size(); i++) {
					if(allTargetBank.get(i).getNetwork_code().contains(keyword)) {
						searchTargetBank.add(allTargetBank.get(i)); 
					}
				}
			}else {
				for (int i = 0; i < allTargetBank.size(); i++) {
					if(allTargetBank.get(i).getBank_name().contains(keyword)) {
						searchTargetBank.add(allTargetBank.get(i)); 
						
					}
				}
			}
			return searchTargetBank;
		}
	}
	
	@RequestMapping(value = "/getTargetBankBySknCode", method = RequestMethod.POST)
	public TargetBank getTargetBankBySknCode(@RequestBody ObjectNode object) {
		return service.getTargetBankBySknCode(object.get("bankCode").asText());
	}
	
}
