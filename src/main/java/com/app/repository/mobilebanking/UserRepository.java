package com.app.repository.mobilebanking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);
	User findUserByUsername(String username);
	User findByCustomer(Customer customer);
}
