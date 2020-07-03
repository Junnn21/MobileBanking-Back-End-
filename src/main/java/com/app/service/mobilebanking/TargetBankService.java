package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.TargetBank;
import com.app.repository.mobilebanking.TargetBankRepository;

@Service
public class TargetBankService {

	@Autowired
	private TargetBankRepository repository;
	
	public TargetBank saveNewTargetBank(TargetBank targetBank) {
		return repository.save(targetBank);
	}
	
	public List<TargetBank> getAllTargetBank() {
		return repository.findAll();
	}
	
	public TargetBank getTargetBankById(long id) {
		return repository.findById(id);
	}
	
}
