package com.app.entity.mobilebanking;

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
@Table(name = "target_bank", schema = "mobilebanking")
public class TargetBank extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "bank_name")
	private String bank_name;
	
	@Column(name = "network_code")
	private int network_code;
	
	@Column(name = "skn_code")
	private int skn_code;
	
	@Column(name = "rtgs_code")
	private int rtgs_code;
	
	@Column(name = "network_enabled")
	private int network_enabled;
	
	@Column(name = "skn_enabled")
	private int skn_enabled;
	
	@Column(name = "rtgs_enabled")
	private int rtgs_enabled;
	
	@OneToOne
	@JoinColumn(name = "status")
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

	public int getNetwork_code() {
		return network_code;
	}

	public void setNetwork_code(int network_code) {
		this.network_code = network_code;
	}

	public int getSkn_code() {
		return skn_code;
	}

	public void setSkn_code(int skn_code) {
		this.skn_code = skn_code;
	}

	public int getRtgs_code() {
		return rtgs_code;
	}

	public void setRtgs_code(int rtgs_code) {
		this.rtgs_code = rtgs_code;
	}

	public int getNetwork_enabled() {
		return network_enabled;
	}

	public void setNetwork_enabled(int network_enabled) {
		this.network_enabled = network_enabled;
	}

	public int getSkn_enabled() {
		return skn_enabled;
	}

	public void setSkn_enabled(int skn_enabled) {
		this.skn_enabled = skn_enabled;
	}

	public int getRtgs_enabled() {
		return rtgs_enabled;
	}

	public void setRtgs_enabled(int rtgs_enabled) {
		this.rtgs_enabled = rtgs_enabled;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
