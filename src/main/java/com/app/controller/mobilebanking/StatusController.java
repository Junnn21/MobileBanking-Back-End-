package com.app.controller.mobilebanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Status;
import com.app.repository.mobilebanking.StatusRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class StatusController {

	@Autowired
	private StatusRepository repository;
	
	@RequestMapping(value = "/saveNewStatus", method = RequestMethod.POST)
	public ResponseEntity<Status> saveNewStatus(@RequestBody ObjectNode object){
		Status status = new Status();
		status.setType(object.get("type").asText());
		status.setCode(object.get("code").asText());
		status.setName(object.get("name").asText());
		return new ResponseEntity<>(repository.save(status), HttpStatus.OK);
	}
	
	public Status findStatus(String type, String code) {
		return repository.findByTypeAndCode(type, code);
	}
	
}
