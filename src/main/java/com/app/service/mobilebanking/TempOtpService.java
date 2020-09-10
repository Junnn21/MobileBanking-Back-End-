package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.TempOtp;
import com.app.repository.mobilebanking.TempOtpRepository;

@Service
public class TempOtpService {

	@Autowired
	private TempOtpRepository repository;
	
	public List<TempOtp> getTempOtp(){
		return repository.findAll();
	}
	
	public TempOtp saveTempOtp(TempOtp tempOtp) {
		return repository.save(tempOtp);
	}
	
	public List<TempOtp> getTempOtpByTypeAndCustomer(Lookup type, Customer customer){
		return repository.findByTypeAndCustomer(type, customer);
	}
	
	public List<TempOtp> getTempOtpByTypeAndCustomerDummy(Lookup type, CustomerDummy customerDummy){
		return repository.findByTypeAndCustomerDummy(type, customerDummy);
	}
	
}
