package com.app.controller.mobilebanking;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.corebankingdummy.CustomerDummyController;
import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.RegisterOtp;
import com.app.service.mobilebanking.MailService;
import com.app.service.mobilebanking.RegisterOtpService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class RegisterOtpController {

	@Autowired
	private RegisterOtpService service;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CustomerDummyController customerDummyController;
	
	public static boolean compareDates(Date date1,Date date2){
        if(date1.after(date2)){
            return false;
        }else if(date1.before(date2)){
           	return true;
        }else if(date1.equals(date2)){
            return true;
        }
        return true;
    }
	
	@RequestMapping(value = "/registerOtp", method = RequestMethod.GET)
	public ResponseEntity<List<RegisterOtp>> registerOtp(){
		return new ResponseEntity<>(service.getRegisterOtp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveRegisterOtp", method = RequestMethod.POST)
	public ResponseEntity<RegisterOtp> saveRegisterOtp(@RequestBody ObjectNode object){
		RegisterOtp registerOtp = new RegisterOtp();
		//created date
		Date createdDate = new Date();
		//expired date
		Calendar expired = Calendar.getInstance();
		expired.add(Calendar.MINUTE, 5);
		Date expiredDate = expired.getTime();
		//token
		Random random = new Random();
		int number = 100000 + random.nextInt(900000);
		String token = Integer.toString(number);
		//user
		CustomerDummy customerDummy = customerDummyController.getCustomerDummyById(object.get("customer").asLong());
		
		registerOtp.setCreated_date(createdDate);
		registerOtp.setExpired_date(expiredDate);
		registerOtp.setToken(token);
		registerOtp.setCustomerDummy(customerDummy);
		mailService.sendMail(customerDummy, registerOtp);
		return new ResponseEntity<>(service.saveRegisterOtp(registerOtp), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateRegisterOtp", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateRegisterOtp(@RequestBody ObjectNode object){
		CustomerDummy customerDummy = customerDummyController.getCustomerDummyById(object.get("customer").asLong());
		List<RegisterOtp> list = service.getRegisterOtpByCustomerDummy(customerDummy);
		RegisterOtp data = list.get(list.size()-1);
		Date now = new Date();
		
		if(data.getToken().equals(object.get("token").asText())) {
			if(compareDates(now, data.getExpired_date())) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(false, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(false, HttpStatus.OK);
		
	}
	
}
