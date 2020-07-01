package com.app.service.corebankingdummy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.repository.corebankingdummy.AccountDummyRepository;

@Service
public class AccountDummyService {

	@Autowired
	private AccountDummyRepository repository;
	
	public List<AccountDummy> getAllAccountDummy(){
		return repository.findAll();
	}
	
	public AccountDummy saveNewAccountDummy(AccountDummy accountDummy) {
		return repository.save(accountDummy);
	}
	
	public List<AccountDummy> findAccountDummyByCustomer(CustomerDummy customer){
		return repository.findAccountDummyByCustomer(customer);
	}
	
	public AccountDummy findAccountDummyByAccountNumber(String accountNumber) {
		return repository.findAccountDummyByAccountNumber(accountNumber);
	}
	
}
