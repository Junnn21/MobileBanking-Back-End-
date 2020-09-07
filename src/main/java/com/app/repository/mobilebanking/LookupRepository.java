package com.app.repository.mobilebanking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.mobilebanking.Lookup;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long>{
	Lookup findById(long id);
	List<Lookup> findByType(String type);
}
