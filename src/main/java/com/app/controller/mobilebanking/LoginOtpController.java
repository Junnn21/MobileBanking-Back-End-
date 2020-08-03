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

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.LoginOtp;
import com.app.service.mobilebanking.LoginOtpService;
import com.app.service.mobilebanking.MailService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class LoginOtpController {

	@Autowired
	private LoginOtpService service;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CustomerController customerController;
	
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
	
	@RequestMapping(value = "/saveLoginOtp", method = RequestMethod.POST)
	public ResponseEntity<LoginOtp> saveLoginOtp(@RequestBody ObjectNode object){
		LoginOtp loginOtp = new LoginOtp();
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
		
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		
		loginOtp.setCreated_date(createdDate);
		loginOtp.setExpired_date(expiredDate);
		loginOtp.setToken(token);
		loginOtp.setCustomer(customer);
		mailService.sendLoginOtp(customer, loginOtp);
		return new ResponseEntity<>(service.saveLoginOtp(loginOtp), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateLoginOtp", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateRegisterOtp(@RequestBody ObjectNode object){
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		List<LoginOtp> list = service.getLoginOtpByCustomer(customer);
		LoginOtp data = list.get(list.size()-1);
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
