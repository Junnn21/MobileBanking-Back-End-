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
@Table(name = "target_bank", schema = "mobilebanking")
public class TargetBank extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "bank_name")
	private String bank_name;
	
	@Column(name = "network_code")
	private String network_code;
	
	@Column(name = "skn_code")
	private String sknCode;
	
	@Column(name = "rtgs_code")
	private String rtgs_code;
	
	@Column(name = "network_enabled")
	private String network_enabled;
	
	@Column(name = "skn_enabled")
	private String skn_enabled;
	
	@Column(name = "rtgs_enabled")
	private String rtgs_enabled;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "status")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getNetwork_code() {
		return network_code;
	}

	public void setNetwork_code(String network_code) {
		this.network_code = network_code;
	}

	public String getSknCode() {
		return sknCode;
	}

	public void setSknCode(String sknCode) {
		this.sknCode = sknCode;
	}

	public String getRtgs_code() {
		return rtgs_code;
	}

	public void setRtgs_code(String rtgs_code) {
		this.rtgs_code = rtgs_code;
	}

	public String getNetwork_enabled() {
		return network_enabled;
	}

	public void setNetwork_enabled(String network_enabled) {
		this.network_enabled = network_enabled;
	}

	public String getSkn_enabled() {
		return skn_enabled;
	}

	public void setSkn_enabled(String skn_enabled) {
		this.skn_enabled = skn_enabled;
	}

	public String getRtgs_enabled() {
		return rtgs_enabled;
	}

	public void setRtgs_enabled(String rtgs_enabled) {
		this.rtgs_enabled = rtgs_enabled;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
