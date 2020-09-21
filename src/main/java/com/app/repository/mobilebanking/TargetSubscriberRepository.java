package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TargetSubscriber;

@Repository
public interface TargetSubscriberRepository extends JpaRepository<TargetSubscriber, Long> {
	List<TargetSubscriber> findByCustomerAndStatus(Customer customer, Status status);
	List<TargetSubscriber> findByCustomerAndStatusAndNameContainingIgnoreCase(Customer customer, Status status, String keyword);
	List<TargetSubscriber> findByCustomerAndStatusAndSubscriberNumberContaining(Customer customer, Status status, String keyword);
	List<TargetSubscriber> findByCustomerAndMerchantAndStatus(Customer customer, BillpaymentMerchant merchant, Status status);
	List<TargetSubscriber> findByCustomerAndMerchantAndStatusAndNameContainingIgnoreCase(Customer customer, BillpaymentMerchant merchant, Status status, String keyword);
	List<TargetSubscriber> findByCustomerAndMerchantAndStatusAndSubscriberNumberContaining(Customer customer, BillpaymentMerchant merchant, Status status, String keyword);
	TargetSubscriber findById(long id);
	TargetSubscriber findBySubscriberNumberAndMerchant(String subscriberNumber, BillpaymentMerchant merchant);
}
