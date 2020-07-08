package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.TransCharge;
import com.app.repository.mobilebanking.TransChargeRepository;

@Service
public class TransChargeService {
	
	@Autowired
	private TransChargeRepository repository;
	
	public List<TransCharge> getAllTransCharge(){
		return repository.findAll();
	}
	
	public TransCharge getTransChargeById(long id) {
		return repository.getTransChargeById(id);
	}
	
	public TransCharge saveNewTransCharge(TransCharge transCharge) {
		return repository.save(transCharge);
	}
}
