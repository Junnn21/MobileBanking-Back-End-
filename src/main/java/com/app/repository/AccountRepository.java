package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Account;
import com.app.entity.Customer;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	Account findAccountByAccountNumber(String account_number);
	List<Account> getListAccountByCustomer(Customer customer);
}
