package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
	Status findById(long id);
	List<Status> findByType(String type);
}
