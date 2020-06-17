package com.app.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.auditing.Auditable;

@Entity
@Table(name = "trans_charge")
public class TransCharge extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "transaction_type")
	private String transaction_type;
	
	@Column(name = "merchant_mode")
	private String merchant_mode;
	
	@OneToOne
	@JoinColumn(name = "relation_to_bank")
	private Lookup relation_to_bank;
	
	@Column(name = "cif_code")
	private String cif_code;
	
	@Column(name = "charge_amount")
	private Double charge_amount;
	
	@Column(name = "effective_date")
	private Timestamp effective_date;
	
	@Column(name = "reference_key")
	private String reference_key;
	
	@OneToOne
	@JoinColumn(name = "status")
	private Status status;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "effective_hour")
	private int effective_hour;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getMerchant_mode() {
		return merchant_mode;
	}

	public void setMerchant_mode(String merchant_mode) {
		this.merchant_mode = merchant_mode;
	}

	public Lookup getRelation_to_bank() {
		return relation_to_bank;
	}

	public void setRelation_to_bank(Lookup relation_to_bank) {
		this.relation_to_bank = relation_to_bank;
	}

	public String getCif_code() {
		return cif_code;
	}

	public void setCif_code(String cif_code) {
		this.cif_code = cif_code;
	}

	public Double getCharge_amount() {
		return charge_amount;
	}

	public void setCharge_amount(Double charge_amount) {
		this.charge_amount = charge_amount;
	}

	public Timestamp getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(Timestamp effective_date) {
		this.effective_date = effective_date;
	}

	public String getReference_key() {
		return reference_key;
	}

	public void setReference_key(String reference_key) {
		this.reference_key = reference_key;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getEffective_hour() {
		return effective_hour;
	}

	public void setEffective_hour(int effective_hour) {
		this.effective_hour = effective_hour;
	}
	
}
