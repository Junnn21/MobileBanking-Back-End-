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
import org.springframework.data.annotation.CreatedDate;

import com.app.entity.corebankingdummy.CustomerDummy;

@Entity
@Table(name = "temp_otp", schema="mobilebanking", catalog="mobilebanking")
public class TempOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "type")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Lookup type;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "customer_dummy")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private CustomerDummy customerDummy;
	
	@Column(name = "token")
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date created_date;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expired_date")
	private Date expired_date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Lookup getType() {
		return type;
	}

	public void setType(Lookup type) {
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerDummy getCustomerDummy() {
		return customerDummy;
	}

	public void setCustomerDummy(CustomerDummy customerDummy) {
		this.customerDummy = customerDummy;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getExpired_date() {
		return expired_date;
	}

	public void setExpired_date(Date expired_date) {
		this.expired_date = expired_date;
	}
	
}
