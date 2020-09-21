package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.Status;
import com.app.entity.mobilebanking.TargetSubscriber;
import com.app.repository.mobilebanking.TargetSubscriberRepository;

@Service
public class TargetSubscriberService {

	@Autowired
	private TargetSubscriberRepository repository;
	
	public TargetSubscriber findById(long id) {
		return repository.findById(id);
	}
	
	public TargetSubscriber saveNewTargetSubscriber(TargetSubscriber targetSubscriber) {
		return repository.save(targetSubscriber);
	}
	
	public List<TargetSubscriber> getTargetSubscriber(Customer customer, Status status){
		return repository.findByCustomerAndStatus(customer, status);
	}
	
	public List<TargetSubscriber> getTargetSubscriberNameSearched(Customer customer, Status status, String keyword){
		return repository.findByCustomerAndStatusAndNameContainingIgnoreCase(customer, status, keyword);
	}
	
	public List<TargetSubscriber> getTargetSubscriberSubscriberNumberSearched(Customer customer, Status status, String keyword){
		return repository.findByCustomerAndStatusAndSubscriberNumberContaining(customer, status, keyword);
	}
	
	public List<TargetSubscriber> getTargetSubscriberByMerhant(Customer customer, BillpaymentMerchant merchant, Status status){
		return repository.findByCustomerAndMerchantAndStatus(customer, merchant, status);
	}
	
	public List<TargetSubscriber> getTargetSubscriberByMerchantNameSearched(Customer customer, BillpaymentMerchant merchant, Status status, String keyword){
		return repository.findByCustomerAndMerchantAndStatusAndNameContainingIgnoreCase(customer, merchant, status, keyword);
	}
	
	public List<TargetSubscriber> getTargetSubscriberByMerchantSubscriberNumberSearched(Customer customer, BillpaymentMerchant merchant, Status status, String keyword){
		return repository.findByCustomerAndMerchantAndStatusAndSubscriberNumberContaining(customer, merchant, status, keyword);
	}
	
	public TargetSubscriber getTargetSubscriberBySubscriberNumberAndMerchant(String subscriberNumber, BillpaymentMerchant merchant) {
		return repository.findBySubscriberNumberAndMerchant(subscriberNumber, merchant);
	}
	
}
