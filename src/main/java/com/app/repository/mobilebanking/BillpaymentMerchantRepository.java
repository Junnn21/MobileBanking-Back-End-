package com.app.repository.mobilebanking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.BillpaymentMerchant;

@Repository
public interface BillpaymentMerchantRepository extends JpaRepository<BillpaymentMerchant, Long>{
	BillpaymentMerchant findBillpaymentMerchantByCode(String code);
}
