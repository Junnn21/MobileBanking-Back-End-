package com.app.entity.corebankingdummy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.auditing.Auditable;

@Entity
@Table(name = "customer_dummy", catalog = "corebankingdummy", schema = "corebankingdummy")
public class CustomerDummy extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "cif_code", unique = true)
	private String cifCode;
	
	@Column(name = "full_name")
	private String full_name;
	
	@Column(name = "mobile_number")
	private String mobile_number;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "mothers_maiden")
	private String mothers_maiden;
	
	@Column(name = "birth_place")
	private String birth_place;
	
	@Column(name = "birth_date")
	private Date birth_date;
	
	@Column(name = "occupation")
	private String occupation;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "id_number")
	private String id_number;
	
	@Column(name = "pan")
	private String pan;
	
	@Column(name = "pin")
	private String pin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCifCode() {
		return cifCode;
	}

	public void setCifCode(String cif_code) {
		this.cifCode = cif_code;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMothers_maiden() {
		return mothers_maiden;
	}

	public void setMothers_maiden(String mothers_maiden) {
		this.mothers_maiden = mothers_maiden;
	}

	public String getBirth_place() {
		return birth_place;
	}

	public void setBirth_place(String birth_place) {
		this.birth_place = birth_place;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
