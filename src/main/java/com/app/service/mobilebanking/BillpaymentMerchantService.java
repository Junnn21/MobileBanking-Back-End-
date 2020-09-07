package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.repository.mobilebanking.BillpaymentMerchantRepository;

@Service
public class BillpaymentMerchantService {

	@Autowired
	private BillpaymentMerchantRepository repository;
	
	public List<BillpaymentMerchant> getAllMerchant(){
		return repository.findAll();
	}
	
	public BillpaymentMerchant findByCode(String code) {
		return repository.findBillpaymentMerchantByCode(code);
	}
	
	public BillpaymentMerchant saveNewBillpaymentMerchant(BillpaymentMerchant billpaymentMerchant) {
		return repository.save(billpaymentMerchant);
	}
	
}
