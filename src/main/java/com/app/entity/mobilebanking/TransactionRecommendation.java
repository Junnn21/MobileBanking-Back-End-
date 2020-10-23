package com.app.entity.mobilebanking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_recommendation", schema = "mobilebanking")
public class TransactionRecommendation {
	
	@Id
	@Column(name = "id")
	private String id = "A";
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@Column(name = "target_account_subscriber")
	private String target_account_subscriber;
	
	@Column(name = "target_name")
	private String target_name;
	
	@ManyToOne
	@JoinColumn(name = "target_bank")
	private TargetBank target_bank;
	
	@ManyToOne
	@JoinColumn(name = "target_merchant")
	private BillpaymentMerchant target_merchant;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "count")
	private int count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getTarget_account_subscriber() {
		return target_account_subscriber;
	}

	public void setTarget_account_subscriber(String target_account_subscriber) {
		this.target_account_subscriber = target_account_subscriber;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public TargetBank getTarget_bank() {
		return target_bank;
	}

	public void setTarget_bank(TargetBank target_bank) {
		this.target_bank = target_bank;
	}

	public BillpaymentMerchant getTarget_merchant() {
		return target_merchant;
	}

	public void setTarget_merchant(BillpaymentMerchant target_merchant) {
		this.target_merchant = target_merchant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
