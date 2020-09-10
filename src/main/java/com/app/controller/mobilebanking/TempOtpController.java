package com.app.controller.mobilebanking;

import java.util.Date;
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
import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.TempOtp;
import com.app.function.Function;
import com.app.service.mobilebanking.MailService;
import com.app.service.mobilebanking.TempOtpService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TempOtpController {

	@Autowired
	private TempOtpService service;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private CustomerDummyController customerDummyController;
	
	@Autowired
	private LookupController lookupController;
	
	@RequestMapping(value = "/saveTempOtp", method = RequestMethod.POST)
	public ResponseEntity<TempOtp> saveLoginOtp(@RequestBody ObjectNode object){
		TempOtp tempOtp = new TempOtp();
		Date createdDate = new Date();
		Date expiredDate = Function.generateExpiredDate();
		String token = Function.generateOtpToken();
		
		Lookup type = lookupController.findLookUpByTypeAndCode("temp_otp", object.get("code").asText());
		
		tempOtp.setCreated_date(createdDate);
		tempOtp.setExpired_date(expiredDate);
		tempOtp.setToken(token);
		tempOtp.setType(type);
		
		if(object.get("code").asText().equals("REGISTER")) {
			CustomerDummy customerDummy = customerDummyController.findByCifCode(object.get("cif_code").asText());
			tempOtp.setCustomerDummy(customerDummy);
			mailService.sendRegisterOtp(customerDummy, tempOtp);
		} else {
			Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
			tempOtp.setCustomer(customer);
			if(object.get("code").asText().equals("FUNDTRANSFER")) {
				Double totalAmount = object.get("totalAmount").asDouble();
				String currency = object.get("currency").asText();
				String accNumber = object.get("accNumber").asText();
				String accName = object.get("accName").asText();
				String bankName = object.get("bankName").asText();
				mailService.sendTransferOtp(customer, tempOtp, totalAmount, currency, accNumber, accName, bankName);
			}else if (object.get("code").asText().equals("LOGIN")) {
				mailService.sendLoginOtp(customer, tempOtp);
			}
			
		}
		
		return new ResponseEntity<>(service.saveTempOtp(tempOtp), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateTempOtp", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateTempOtp(@RequestBody ObjectNode object){
		if(object.get("code").asText().equals("REGISTER")) {
			Lookup type = lookupController.findLookUpByTypeAndCode("temp_otp", object.get("code").asText());
			CustomerDummy customerDummy = customerDummyController.findByCifCode(object.get("cif_code").asText());
			List<TempOtp> otpList = service.getTempOtpByTypeAndCustomerDummy(type, customerDummy);
			TempOtp data = otpList.get(otpList.size() -1);
			Date now = new Date();
			
			if(data.getToken().equals(object.get("token").asText())) {
				if(Function.compareDates(now, data.getExpired_date())) {
					return new ResponseEntity<>(true, HttpStatus.OK);
				}else {
					return new ResponseEntity<>(false, HttpStatus.OK);
				}
			}
		}else{
			Lookup type = lookupController.findLookUpByTypeAndCode("temp_otp", object.get("code").asText());
			Customer customer = customerController.getByCifCode(object.get("cif_code").asText());
			List<TempOtp> otpList = service.getTempOtpByTypeAndCustomer(type, customer);
			TempOtp data = otpList.get(otpList.size() -1);
			Date now = new Date();
			
			if(data.getToken().equals(object.get("token").asText())) {
				if(Function.compareDates(now, data.getExpired_date())) {
					return new ResponseEntity<>(true, HttpStatus.OK);
				}else {
					return new ResponseEntity<>(false, HttpStatus.OK);
				}
			}
		}
		
		return new ResponseEntity<>(false, HttpStatus.OK);
	}
	
}
