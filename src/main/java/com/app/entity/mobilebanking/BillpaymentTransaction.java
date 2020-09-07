package com.app.entity.mobilebanking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.app.auditing.Auditable;

@Entity
@Table(name = "billpayment_transaction", schema = "mobilebanking")
public class BillpaymentTransaction extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "transaction_reference_number")
	private String transaction_reference_number;
	
	@Column(name = "bank_reference_number")
	private String bank_reference_number;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "transaction_type")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Lookup transaction_type;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "merchant")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BillpaymentMerchant merchant;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "debit_account")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Account debit_account;
	
	@Column(name = "customer_number")
	private String customer_number;
	
	@Column(name = "customer_name")
	private String customer_name;
	
	@Column(name = "billing_period")
	private String billing_period;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "billing_due_date")
	private Date billing_due_date;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "payment_amount")
	private Double payment_amount;
	
	@Column(name = "bank_charge")
	private Double bank_charge;
	
	@Column(name = "total_amount_debited")
	private Double total_amount_debited;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "status")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Status status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date")
	private Date transaction_date;

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

	public BillpaymentMerchant getMerchant() {
		return merchant;
	}

	public void setMerchant(BillpaymentMerchant merchant) {
		this.merchant = merchant;
	}

	public Account getDebit_account() {
		return debit_account;
	}

	public void setDebit_account(Account debit_account) {
		this.debit_account = debit_account;
	}

	public String getCustomer_number() {
		return customer_number;
	}

	public void setCustomer_number(String customer_number) {
		this.customer_number = customer_number;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getBilling_period() {
		return billing_period;
	}

	public void setBilling_period(String billing_period) {
		this.billing_period = billing_period;
	}

	public Date getBilling_due_date() {
		return billing_due_date;
	}

	public void setBilling_due_date(Date billing_due_date) {
		this.billing_due_date = billing_due_date;
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

	public Double getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(Double payment_amount) {
		this.payment_amount = payment_amount;
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

	public Date getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}
	
}
