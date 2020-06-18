package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Status;
import com.app.repository.StatusRepository;

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
