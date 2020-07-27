package com.app.repository.mobilebanking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findById(long id);
	Customer findCustomerByCifCode(String cif_code);
}
