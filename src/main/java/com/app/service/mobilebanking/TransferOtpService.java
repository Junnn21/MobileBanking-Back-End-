package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TransferOtp;
import com.app.repository.mobilebanking.TransferOtpRepository;

@Service
public class TransferOtpService {

	@Autowired
	private TransferOtpRepository repository;
	
	public List<TransferOtp> getTransferOtp(){
		return repository.findAll();
	}
	
	public TransferOtp saveTransferOtp(TransferOtp transferOtp) {
		return repository.save(transferOtp);
	}
	
	public List<TransferOtp>getTransferOtpByCustomer(Customer customer){
		return repository.getListTransferOtpByCustomer(customer);
	}
	
}
