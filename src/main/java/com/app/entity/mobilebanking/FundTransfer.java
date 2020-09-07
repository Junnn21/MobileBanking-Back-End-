package com.app.entity.mobilebanking;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.auditing.Auditable;

@Entity
@Table(name = "fund_transfer", schema = "mobilebanking")
public class FundTransfer extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "transaction_reference_number")
	private String transaction_reference_number;
	
	@Column(name = "bank_reference_number")
	private String bank_reference_number;
	
	@ManyToOne
	@JoinColumn(name = "transaction_type")
	private Lookup transaction_type;
	
	@ManyToOne
	@JoinColumn(name = "account")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "target_account")
	private TargetAccount target_account;
	
	@ManyToOne
	@JoinColumn(name = "target_bank")
	private TargetBank target_bank;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "bank_charge")
	private double bank_charge;
	
	@Column(name = "total_amount_debited")
	private double total_amount_debited;
	
	@ManyToOne
	@JoinColumn(name = "status")
	private Status status;
	
	@Column(name = "transfer_date")
	private Timestamp transfer_date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransaction_reference_number() {
		return transaction_reference_number;
	}

	public void setTransaction_reference_number(String transaction_reference_number) {
		this.transaction_reference_number = transaction_reference_number;
	}

	public String getBank_reference_number() {
		return bank_reference_number;
	}

	public void setBank_reference_number(String bank_reference_number) {
		this.bank_reference_number = bank_reference_number;
	}

	public Lookup getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(Lookup transaction_type) {
		this.transaction_type = transaction_type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TargetAccount getTarget_account() {
		return target_account;
	}

	public void setTarget_account(TargetAccount target_account) {
		this.target_account = target_account;
	}

	public TargetBank getTarget_bank() {
		return target_bank;
	}

	public void setTarget_bank(TargetBank target_bank) {
		this.target_bank = target_bank;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Double getBank_charge() {
		return bank_charge;
	}

	public void setBank_charge(Double bank_charge) {
		this.bank_charge = bank_charge;
	}

	public Double getTotal_amount_debited() {
		return total_amount_debited;
	}

	public void setTotal_amount_debited(Double total_amount_debited) {
		this.total_amount_debited = total_amount_debited;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Timestamp getTransfer_date() {
		return transfer_date;
	}

	public void setTransfer_date(Timestamp transfer_date) {
		this.transfer_date = transfer_date;
	}	
	
}
