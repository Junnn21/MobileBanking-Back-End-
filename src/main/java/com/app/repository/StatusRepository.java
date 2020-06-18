package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
	Status findById(long id);
}
