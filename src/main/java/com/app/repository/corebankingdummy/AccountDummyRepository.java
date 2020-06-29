package com.app.repository.corebankingdummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.AccountDummy;

@Repository
public interface AccountDummyRepository extends JpaRepository<AccountDummy, Long> {
	AccountDummy findAccountDummyById(long id);
}
