package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.TransCharge;

@Repository
public interface TransChargeRepository extends JpaRepository<TransCharge, Long>{
	TransCharge getTransChargeById(long id);
}
