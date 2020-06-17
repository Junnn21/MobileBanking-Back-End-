package com.app.auditing;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

	@CreatedBy
	protected U created_by;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date created_date;
	
	@LastModifiedBy
	protected U last_updated_by;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date last_updated_date;

	public U getCreated_by() {
		return created_by;
	}

	public void setCreated_by(U created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public U getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(U last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public Date getLast_updated_date() {
		return last_updated_date;
	}

	public void setLast_updated_date(Date last_updated_date) {
		this.last_updated_date = last_updated_date;
	}
	
}
