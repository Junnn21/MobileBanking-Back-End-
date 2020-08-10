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
import com.app.entity.mobilebanking.TransferOtp;
import com.app.service.mobilebanking.MailService;
import com.app.service.mobilebanking.TransferOtpService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TransferOtpController {

	@Autowired
	private TransferOtpService service;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private MailService mailService;
	
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
	
	@RequestMapping(value = "/transferOtp", method = RequestMethod.GET)
	public ResponseEntity<List<TransferOtp>> transferOtp(){
		return new ResponseEntity<>(service.getTransferOtp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveTransferOtp", method = RequestMethod.POST)
	public ResponseEntity<TransferOtp> saveTransferOtp(@RequestBody ObjectNode object){
		TransferOtp transferOtp = new TransferOtp();
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
		Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
		Double totalAmount = object.get("totalAmount").asDouble();
		String currency = object.get("currency").asText();
		String accNumber = object.get("accNumber").asText();
		String accName = object.get("accName").asText();
		String bankName = object.get("bankName").asText();
		
		transferOtp.setCreated_date(createdDate);
		transferOtp.setExpired_date(expiredDate);
		transferOtp.setToken(token);
		transferOtp.setCustomer(customer);
		mailService.sendTransferOtp(customer, transferOtp, totalAmount, currency, accNumber, accName, bankName);
		return new ResponseEntity<>(service.saveTransferOtp(transferOtp), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateTransferOtp", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateTransferOtp(@RequestBody ObjectNode object){
		Customer customer = customerController.getCustomerById(object.get("customer").asLong());
		List<TransferOtp> list = service.getTransferOtpByCustomer(customer);
		TransferOtp data = list.get(list.size()-1);
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
