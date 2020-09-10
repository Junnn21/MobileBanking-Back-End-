package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Lookup;
import com.app.repository.mobilebanking.LookupRepository;

@Service
public class LookupService {

	@Autowired
	private LookupRepository repository;
	
	public List<Lookup> getAllLookup(){
		return repository.findAll();
	}
	
	public Lookup saveNewLookup(Lookup lookup) {
		return repository.save(lookup);
	}
	
	public Lookup getLookupById(long id) {
		return repository.findById(id);
	}
	
	public List<Lookup> getLookupByType(String type) {
		return repository.findByType(type);
	}
	
	public Lookup getLookupByTypeAndCode(String type, String code) {
		return repository.findByTypeAndCode(type, code);
	}
}
