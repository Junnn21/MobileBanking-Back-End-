package com.app.controller.mobilebanking;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.corebankingdummy.CustomerDummyController;
import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.User;
import com.app.function.Function;
import com.app.repository.mobilebanking.StatusRepository;
import com.app.service.mobilebanking.UserService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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
		CustomerDummy customerDummy = customerDummyController.getCustomerDummyById(object.get("customer").asLong());
		
		List<Status> statusList = statusRepo.findByType("user");
		Status status = new Status();
		for (int i = 0; i < statusList.size(); i++) {
			if(statusList.get(i).getCode().equals("aktif")) {
				status = statusList.get(i);
			}
		}
		
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
	
//	@GetMapping(value = "/testCreateSession")
//	public String createNewSession(HttpSession session, HttpServletRequest request) {
//        return request.getSession().getId();
//	}
//	
//	@PostMapping(value = "/testUpdateSession")
//	public String updateSession(HttpServletRequest request) {
//		ObjectNode object = JsonNodeFactory.instance.objectNode();
//		object.put("email", "1fff");
//        request.getSession().setAttribute("cif_code", object);
//		return request.getSession().getId();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@GetMapping(value = "/testGetSession")
//    public String home(HttpSession session) {
//        return session.getId();    
//    }
//	
//	@PostMapping(value = "/testDestroySession")
//    public String destroySession(HttpServletRequest request, @RequestBody String key) {
//        request.getSession().invalidate();
//        return "Deleted";
//    }
	
}
