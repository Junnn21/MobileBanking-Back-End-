package com.app.repository.corebankingdummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.CustomerDummy;

@Repository
public interface CustomerDummyRepository extends JpaRepository<CustomerDummy, Long> {
	CustomerDummy findCustomerDummyById(long id);
	CustomerDummy findCustomerDummyByCifCode(String cif_code);
}
