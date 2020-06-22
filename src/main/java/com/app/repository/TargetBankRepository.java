package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.TargetBank;

@Repository
public interface TargetBankRepository extends JpaRepository<TargetBank, Long> {
	TargetBank findById(long id);
}
