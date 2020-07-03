package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.FundTransfer;
import com.app.repository.mobilebanking.FundTransferRepository;

@Service
public class FundTransferService {
	
	@Autowired
	private FundTransferRepository repository;
	
	public List<FundTransfer> getAllFundTransfer(){
		return repository.findAll();
	}
	
	public FundTransfer saveNewFundTransfer(FundTransfer fundTransfer) {
		return repository.save(fundTransfer);
	}
}
