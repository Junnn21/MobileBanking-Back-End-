package com.app.repository.corebankingdummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.corebankingdummy.BillpaymentAccountDummy;

@Repository
public interface BillpaymentAccountDummyRepository extends JpaRepository<BillpaymentAccountDummy, Long> {

}
