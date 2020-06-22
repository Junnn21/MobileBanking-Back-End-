package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Customer;
import com.app.entity.Status;
import com.app.entity.User;
import com.app.repository.CustomerRepository;
import com.app.repository.StatusRepository;
import com.app.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	//tambahin user baru
	@RequestMapping(value = "/saveNewUser", method = RequestMethod.POST)
	public ResponseEntity<User> saveNewUser(@RequestBody ObjectNode object){
		Status status = statusRepo.findById(object.get("status").asLong());
		Customer customer = customerRepo.findById(object.get("customer").asLong());
		if(status != null && customer != null) {
			User newUser = new User();
			newUser.setEmail(object.get("email").asText());
			newUser.setName(object.get("name").asText());
			newUser.setPassword(object.get("password").asText());
			newUser.setUsername(object.get("username").asText());
			newUser.setCustomer(customer);
			newUser.setStatus(status);
			return new ResponseEntity<>(service.saveNewUser(newUser), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
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
