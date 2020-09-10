package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TargetSubscriber;

@Repository
public interface TargetSubscriberRepository extends JpaRepository<TargetSubscriber, Long> {
	List<TargetSubscriber> findByCustomer(Customer customer);
	TargetSubscriber findById(long id);
}
