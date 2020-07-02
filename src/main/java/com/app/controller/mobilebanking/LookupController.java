package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.mobilebanking.Lookup;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.LookupService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class LookupController {

	@Autowired
	private LookupService service;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@RequestMapping(value = "/lookup", method = RequestMethod.GET)
	public List<Lookup> getAllLookup(){
		return service.getAllLookup();
	}
	
	@RequestMapping(value = "/saveNewLookup", method = RequestMethod.POST)
	public Lookup saveNewLookup(@RequestBody ObjectNode object) {
		Lookup newLookup = new Lookup();
		newLookup.setType(object.get("type").asText());
		newLookup.setCode(object.get("code").asText());
		newLookup.setName(object.get("name").asText());
		newLookup.setStatus(statusRepository.findById(object.get("status").asLong()));
		
		return service.saveNewLookup(newLookup);
	}
}
