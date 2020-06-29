package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Account;
import com.app.entity.mobilebanking.Customer;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	Account findAccountByAccountNumber(String account_number);
	List<Account> getListAccountByCustomer(Customer customer);
}
