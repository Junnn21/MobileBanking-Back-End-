package com.app.service.corebankingdummy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.BillpaymentAccountDummy;
import com.app.repository.corebankingdummy.BillpaymentAccountDummyRepository;

@Service
public class BillpaymentAccountDummyService {

	@Autowired
	private BillpaymentAccountDummyRepository repository;
	
	public List<BillpaymentAccountDummy> getAllBillpaymentAccountDummy(){
		return repository.findAll();
	}
	
	public BillpaymentAccountDummy saveNewBillpaymentAccountDummy(BillpaymentAccountDummy billpaymentAccountDummy) {
		return repository.save(billpaymentAccountDummy);
	}
	
	public List<BillpaymentAccountDummy> findBillpaymentAccountDummyByAccountNumber(String accNumber){
		return repository.findByAccountNumber(accNumber);
	}
	
}
