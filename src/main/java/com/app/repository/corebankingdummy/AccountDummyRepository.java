package com.app.repository.corebankingdummy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.AccountDummy;
import com.app.entity.corebankingdummy.CustomerDummy;

@Repository
public interface AccountDummyRepository extends JpaRepository<AccountDummy, Long> {
	AccountDummy findAccountDummyById(long id);
	List<AccountDummy> findAccountDummyByCustomer(CustomerDummy customer);
	AccountDummy findAccountDummyByAccountNumber(String accountNumber);
}
