package com.app.entity.mobilebanking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "target_account", schema = "mobilebanking")
public class TargetAccount extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "bank_detail")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TargetBank bank_detail;
	
	@Column(name = "account_number")
	private String account_number;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
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

	public TargetBank getBank_detail() {
		return bank_detail;
	}

	public void setBank_detail(TargetBank bank_detail) {
		this.bank_detail = bank_detail;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
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
