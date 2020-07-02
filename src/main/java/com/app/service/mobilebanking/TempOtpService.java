package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.TempOtp;
import com.app.entity.mobilebanking.User;
import com.app.repository.mobilebanking.TempOtpRepository;

@Service
public class TempOtpService {

	@Autowired
	private TempOtpRepository repository;
	
	public TempOtp saveTempOtp(TempOtp tempOtp) {
		return repository.save(tempOtp);
	}
	
	public List<TempOtp>getTempOtpByUser(User user){
		return repository.getListTempOtpByUser(user);
	}
	
}
