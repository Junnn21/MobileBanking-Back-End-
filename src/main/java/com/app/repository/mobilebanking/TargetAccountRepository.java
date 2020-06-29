package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TargetAccount;

@Repository
public interface TargetAccountRepository extends JpaRepository<TargetAccount, Long> {
	List<TargetAccount> findByCustomer(Customer customer);
}
