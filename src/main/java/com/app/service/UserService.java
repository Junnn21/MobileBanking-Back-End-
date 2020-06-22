package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.User;
import com.app.repository.UserRepository;

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
	
}
