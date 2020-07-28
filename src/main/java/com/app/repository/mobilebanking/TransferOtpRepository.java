package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TransferOtp;

@Repository
public interface TransferOtpRepository extends JpaRepository<TransferOtp, Long> {
	List<TransferOtp> getListTransferOtpByCustomer(Customer customer);
}
