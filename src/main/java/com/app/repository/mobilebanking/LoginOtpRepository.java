package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.LoginOtp;

@Repository
public interface LoginOtpRepository extends JpaRepository<LoginOtp, Long>{
	List<LoginOtp> getListLoginOtpByCustomer(Customer customer);
}
