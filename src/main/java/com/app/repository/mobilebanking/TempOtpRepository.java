package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.TempOtp;
import com.app.entity.mobilebanking.User;

@Repository
public interface TempOtpRepository extends JpaRepository<TempOtp, Long> {
	List<TempOtp> getListTempOtpByUser(User user);
}
