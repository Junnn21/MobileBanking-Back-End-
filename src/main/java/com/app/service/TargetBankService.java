package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.TargetBank;
import com.app.repository.TargetBankRepository;

@Service
public class TargetBankService {

	@Autowired
	private TargetBankRepository repository;
	
	public TargetBank saveNewTargetBank(TargetBank targetBank) {
		return repository.save(targetBank);
	}
	
}
