package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Customer;
import com.app.entity.TargetAccount;

@Repository
public interface TargetAccountRepository extends JpaRepository<TargetAccount, Long> {
	List<TargetAccount> findByCustomer(Customer customer);
}
