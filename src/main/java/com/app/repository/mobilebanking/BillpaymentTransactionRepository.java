package com.app.repository.mobilebanking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.BillpaymentTransaction;

@Repository
public interface BillpaymentTransactionRepository extends JpaRepository<BillpaymentTransaction, Long> {
	BillpaymentTransaction findById(long id);
	
}
