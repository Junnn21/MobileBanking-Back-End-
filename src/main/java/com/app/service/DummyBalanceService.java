package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Account;
import com.app.entity.DummyBalance;
import com.app.repository.DummyBalanceRepository;

@Service
public class DummyBalanceService {
	
	@Autowired
	private DummyBalanceRepository repository;
	
	public List<DummyBalance> getAllDummyBalance(){
		return repository.findAll();
	}
	
	public DummyBalance findDummyBalanceByAccountNumber(Account account) {
		return repository.findDummyBalanceByAccount(account);
	}
	
	public DummyBalance saveNewDummyBalance(DummyBalance dummyBalance) {
		return repository.save(dummyBalance);
	}

}
