package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.RegisterOtp;
import com.app.repository.mobilebanking.RegisterOtpRepository;

@Service
public class RegisterOtpService {

	@Autowired
	private RegisterOtpRepository repository;
	
	public List<RegisterOtp>getRegisterOtp(){
		return repository.findAll();
	}
	
	public RegisterOtp saveRegisterOtp(RegisterOtp registerOtp) {
		return repository.save(registerOtp);
	}
	
	public List<RegisterOtp>getRegisterOtpByCustomerDummy(CustomerDummy customerDummy){
		return repository.getListRegisterOtpByCustomerDummy(customerDummy);
	}
	
}
