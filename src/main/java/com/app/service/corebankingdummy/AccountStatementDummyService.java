package com.app.service.corebankingdummy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.corebankingdummy.AccountStatementDummy;
import com.app.repository.corebankingdummy.AccountStatementDummyRepository;

@Service
public class AccountStatementDummyService {

	@Autowired
	private AccountStatementDummyRepository repository;
	
	public AccountStatementDummy saveAccountStatementDummy(AccountStatementDummy accountStatement) {
		return repository.save(accountStatement);
	}
	
	public List<AccountStatementDummy> getAccountStatementDummyByAccount(AccountDummy account){
		return repository.findAccountStatementDummyByAccount(account);
	}
	
}
