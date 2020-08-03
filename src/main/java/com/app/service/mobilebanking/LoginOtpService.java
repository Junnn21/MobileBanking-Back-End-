package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.LoginOtp;
import com.app.repository.mobilebanking.LoginOtpRepository;

@Service
public class LoginOtpService {

	@Autowired
	private LoginOtpRepository repository;
	
	public List<LoginOtp> getLoginOtp(){
		return repository.findAll();
	}
	
	public LoginOtp saveLoginOtp(LoginOtp loginOtp) {
		return repository.save(loginOtp);
	}
	
	public List<LoginOtp> getLoginOtpByCustomer(Customer customer){
		return repository.getListLoginOtpByCustomer(customer);
	}
	
}
