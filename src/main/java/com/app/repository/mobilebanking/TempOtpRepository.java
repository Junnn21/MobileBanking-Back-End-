package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Lookup;
import com.app.entity.mobilebanking.TempOtp;

@Repository
public interface TempOtpRepository extends JpaRepository<TempOtp, Long> {
	List<TempOtp> findByTypeAndCustomer(Lookup type, Customer customer);
	List<TempOtp> findByTypeAndCustomerDummy(Lookup type, CustomerDummy customerDummy);
}
