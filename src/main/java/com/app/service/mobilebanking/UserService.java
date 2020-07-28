package com.app.service.mobilebanking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.User;
import com.app.repository.mobilebanking.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public User saveNewUser(User user) {
		return repository.save(user);
	}
	
	public List<User> findAllUser(){
		return repository.findAll();
	}
	
	public User findUserByUsername(String username) {
		return repository.findUserByUsername(username);
	}
	
	public User findUserByCustomer(Customer customer) {
		return repository.findByCustomer(customer);
	}
	
}
