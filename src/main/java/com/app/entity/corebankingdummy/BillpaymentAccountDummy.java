package com.app.entity.corebankingdummy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.app.auditing.Auditable;
import com.app.entity.mobilebanking.BillpaymentMerchant;
import com.app.entity.mobilebanking.Status;

@Entity
@Table(name = "billpayment_account_dummy", schema = "corebankingdummy", catalog = "corebankingdummy", uniqueConstraints = @UniqueConstraint(columnNames = {"merchant", "account_number"}))
public class BillpaymentAccountDummy extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "merchant")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BillpaymentMerchant merchant;
	
	@Column(name = "account_number")
	private String account_number;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "billed_amount")
	private Double billed_amount;
	
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

	public BillpaymentMerchant getMerchant() {
		return merchant;
	}

	public void setMerchant(BillpaymentMerchant merchant) {
		this.merchant = merchant;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBilled_amount() {
		return billed_amount;
	}

	public void setBilled_amount(Double billed_amount) {
		this.billed_amount = billed_amount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
