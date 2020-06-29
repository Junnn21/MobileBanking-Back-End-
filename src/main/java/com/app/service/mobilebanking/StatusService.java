package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Status;
import com.app.repository.mobilebanking.StatusRepository;

@Service
public class StatusService {
	
	@Autowired
	private StatusRepository repository;
	
	public List<Status> getAllStatus(){
		return repository.findAll();
	}
	
	public Status saveNewStatus(Status status) {
		return repository.save(status);
	}

}
