package com.app.entity.mobilebanking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.springframework.data.annotation.CreatedDate;

import com.app.entity.corebankingdummy.CustomerDummy;

@Entity
@Table(name = "register_otp", schema = "mobilebanking")
public class RegisterOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;
	
	@Column(name = "token")
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expired_date")
	private Date expired_date;
	
	@ManyToOne
	@JoinColumn(name = "customer_dummy", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private CustomerDummy customerDummy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpired_date() {
		return expired_date;
	}

	public void setExpired_date(Date expired_date) {
		this.expired_date = expired_date;
	}

	public CustomerDummy getCustomerDummy() {
		return customerDummy;
	}

	public void setCustomerDummy(CustomerDummy customerDummy) {
		this.customerDummy = customerDummy;
	}
	
}
