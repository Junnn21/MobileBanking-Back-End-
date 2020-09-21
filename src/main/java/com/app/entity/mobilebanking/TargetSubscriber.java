package com.app.entity.mobilebanking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.app.auditing.Auditable;

@Entity
@Table(name = "target_subscriber", schema = "mobilebanking")
public class TargetSubscriber extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "merchant_detail")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BillpaymentMerchant merchant;
	
	@Column(name = "subscriber_number")
	private String subscriberNumber;
	
	@ManyToOne
	@JoinColumn(name = "status")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Customer customer;
	
	@Column(name = "currency")
	private String currency;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BillpaymentMerchant getMerchant_detail() {
		return merchant;
	}

	public void setMerchant_detail(BillpaymentMerchant merchant) {
		this.merchant = merchant;
	}

	public String getSubscribernumber() {
		return subscriberNumber;
	}

	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
