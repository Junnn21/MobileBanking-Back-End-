package com.app.entity.corebankingdummy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "account_statement_dummy", schema = "corebankingdummy")
@EntityListeners(AuditingEntityListener.class)
public class AccountStatementDummy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "account")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AccountDummy account;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posting_date")
	private Date posting_date;
	
	@Column(name = "transaction_type")
	private String transaction_type;
	
	@Column(name = "transaction_reference_number")
	private String transaction_reference_number;
	
	@Column(name = "detail")
	private String detail;
	
	@Column(name = "customer_note")
	private String customer_note;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "balance_after_transaction")
	private Double balance_after_transaction;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;
	
	@CreatedBy
	private String created_by;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AccountDummy getAccount() {
		return account;
	}

	public void setAccount(AccountDummy account) {
		this.account = account;
	}

	public Date getPosting_date() {
		return posting_date;
	}

	public void setPosting_date(Date posting_date) {
		this.posting_date = posting_date;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getTransaction_reference_number() {
		return transaction_reference_number;
	}

	public void setTransaction_reference_number(String transaction_reference_number) {
		this.transaction_reference_number = transaction_reference_number;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCustomer_note() {
		return customer_note;
	}

	public void setCustomer_note(String customer_note) {
		this.customer_note = customer_note;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance_after_transaction() {
		return balance_after_transaction;
	}

	public void setBalance_after_transaction(Double balance_after_transaction) {
		this.balance_after_transaction = balance_after_transaction;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	
}
