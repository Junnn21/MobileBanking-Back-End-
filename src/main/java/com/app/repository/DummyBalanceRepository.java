package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Account;
import com.app.entity.DummyBalance;

@Repository
public interface DummyBalanceRepository extends JpaRepository<DummyBalance, Account>{
	DummyBalance findDummyBalanceByAccount(Account account);
}
