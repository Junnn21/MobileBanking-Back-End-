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

import com.app.entity.mobilebanking.TempOtp;
import com.app.entity.mobilebanking.User;
import com.app.repository.mobilebanking.UserRepository;
import com.app.service.mobilebanking.TempOtpService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class TempOtpController {

	@Autowired
	private TempOtpService service;
	
	@Autowired
	private UserRepository userRepo;
	
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
	
	@RequestMapping(value = "/tempOtp", method = RequestMethod.GET)
	public ResponseEntity<List<TempOtp>> tempOtp(){
		return new ResponseEntity<>(service.getTempOtp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveTempOtp", method = RequestMethod.POST)
	public ResponseEntity<TempOtp> saveTempOtp(@RequestBody ObjectNode object){
		TempOtp tempOtp = new TempOtp();
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
		User user = userRepo.findById(object.get("user").asLong());
		
		tempOtp.setCreated_date(createdDate);
		tempOtp.setExpired_date(expiredDate);
		tempOtp.setToken(token);
		tempOtp.setUser(user);
		return new ResponseEntity<>(service.saveTempOtp(tempOtp), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateOtp", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateOtp(@RequestBody ObjectNode object){
		User user = userRepo.findById(object.get("user").asLong());
		List<TempOtp> list = service.getTempOtpByUser(user);
		TempOtp data = list.get(list.size()-1);
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
