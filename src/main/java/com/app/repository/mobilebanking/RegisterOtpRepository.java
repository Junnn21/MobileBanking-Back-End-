package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.RegisterOtp;

@Repository
public interface RegisterOtpRepository extends JpaRepository<RegisterOtp, Long> {
	List<RegisterOtp> getListRegisterOtpByCustomerDummy(CustomerDummy customer_dummy);
}
