package com.app.controller.corebankingdummy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.function.Function;
import com.app.service.corebankingdummy.CustomerDummyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class CustomerDummyController {

	@Autowired
	private CustomerDummyService service;
	
	@RequestMapping(value = "/customerDummy", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDummy>> getAllCustomerDummy(){
		return new ResponseEntity<>(service.getAllCustomerDummy(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCustomerDummyByCifCode", method = RequestMethod.POST)
	public ResponseEntity<CustomerDummy> getCustomerDummyByCifCode(@RequestBody ObjectNode object){
		String cif_code = object.get("cif_code").asText();
		return new ResponseEntity<>(service.getCustomerDummyByCifCode(cif_code), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveCustomerDummy", method = RequestMethod.POST)
	public ResponseEntity<CustomerDummy> saveNewCustomerDummy(@RequestBody ObjectNode object){
		List<CustomerDummy> allCustomer = service.getAllCustomerDummy();
		CustomerDummy customer = new CustomerDummy();
		String cifCode = Long.toString(Function.generateCifCode());
		Date date = new Date();
		int flag = 0;
		if(allCustomer.size() != 0) {
			while(flag != 1) {
				for (int i = 0; i < allCustomer.size(); i++) {
					if(allCustomer.get(i).getCifCode().equals(cifCode)) {
						cifCode = Long.toString(Function.generateCifCode());
						flag = 0;
						break;
					}else {
						flag = 1;
					}
				}
			}
		}
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(object.get("birth_date").asText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		customer.setAddress(object.get("address").asText());
		customer.setBirth_date(date);
		customer.setBirth_place(object.get("birth_place").asText());
		customer.setCifCode(cifCode);
		customer.setEmail(object.get("email").asText());
		customer.setFull_name(object.get("full_name").asText());
		customer.setGender(object.get("gender").asText());
		customer.setId_number(object.get("id_number").asText());
		customer.setMobile_number(object.get("mobile_number").asText());
		customer.setMothers_maiden(object.get("mothers_maiden").asText());
		customer.setNationality(object.get("nationality").asText());
		customer.setOccupation(object.get("occupation").asText());
		customer.setPan(object.get("pan").asText());
		customer.setPin(object.get("pin").asText());
		
		return new ResponseEntity<>(service.saveNewCustomer(customer), HttpStatus.OK);
	}
	
}
