package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Account;
import com.app.entity.mobilebanking.Customer;
import com.app.repository.mobilebanking.AccountRepository;


@Service
public class AccountService {
	
	@Autowired
	private AccountRepository repository;
	
	public List<Account> getAllAccount(){
		return repository.findAll();
	}
	
	public Account saveNewAccount(Account account) {
		return repository.save(account);
	}
	
	public Account findAccountByAccountNumber(String accountNumber) {
		return repository.findAccountByAccountNumber(accountNumber);
	}
	
	public List<Account> getListAccountByCustomer(Customer customer){
		return repository.getListAccountByCustomer(customer);
	}
}
