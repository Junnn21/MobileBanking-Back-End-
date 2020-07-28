package com.app.controller.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.corebankingdummy.CustomerDummyController;
import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.User;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private CustomerDummyController customerDummyController;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser(){
		return new ResponseEntity<>(service.findAllUser(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkUsername(@RequestBody ObjectNode object){
		List<User> list = service.findAllUser();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getUsername().equals(object.get("username").asText())) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.OK);
	}
	
	//tambahin user baru
	@RequestMapping(value = "/saveNewUser", method = RequestMethod.POST)
	public ResponseEntity<User> saveNewUser(@RequestBody ObjectNode object){
		Status status = statusRepo.findById(object.get("status").asLong());
		CustomerDummy customerDummy = customerDummyController.getCustomerDummyById(object.get("customer").asLong());
		if(status != null) {
			User newUser = new User();
			newUser.setEmail(customerDummy.getEmail());
			newUser.setName(customerDummy.getFull_name());
			newUser.setPassword(object.get("password").asText());
			newUser.setUsername(object.get("username").asText());
			newUser.setCustomer(customerController.saveCustomer(customerDummy));
			newUser.setStatus(status);
			return new ResponseEntity<>(service.saveNewUser(newUser), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/getUserByCustomer", method = RequestMethod.POST)
	public ResponseEntity<User> getUserByCustomer(@RequestBody ObjectNode object){
		Customer customer = customerController.getCustomerById(object.get("customer").asLong());
		return new ResponseEntity<>(service.findUserByCustomer(customer), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetUserPassword", method = RequestMethod.POST)
	public ResponseEntity<User> resetUserPassword(@RequestBody ObjectNode object){
		User user = service.findUserByUsername(object.get("username").asText());
		user.setPassword(object.get("password").asText());
		return new ResponseEntity<>(service.saveNewUser(user), HttpStatus.OK); 
	}
	
	//login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody ObjectNode object){
		Boolean exist = false;
		List<User> allUser = service.findAllUser();
		User user = new User();
		for (int i = 0; i < allUser.size(); i++) {
			if(allUser.get(i).getUsername().equals(object.get("username").asText())){
				user = allUser.get(i);
				exist = true;
				break;
			}
		}
		
		if(exist == true) {
			if(user.getPassword().equals(object.get("password").asText())) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
