package com.app.repository.corebankingdummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.AccountStatementDummy;

@Repository
public interface AccountStatementDummyRepository extends JpaRepository<AccountStatementDummy, Long> {

}
