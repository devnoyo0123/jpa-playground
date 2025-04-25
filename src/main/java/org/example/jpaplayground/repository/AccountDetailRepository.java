package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

    @Query("SELECT ad FROM AccountDetail ad WHERE ad.balance > :minBalance")
    List<AccountDetail> findByBalanceGreaterThan(@Param("minBalance") BigDecimal minBalance);

    List<AccountDetail> findByIsActiveTrue();

    List<AccountDetail> findByIsActiveFalse();

    @Query("SELECT ad FROM AccountDetail ad JOIN ad.account a WHERE a.accountType = org.example.jpaplayground.domain.AccountType.BUSINESS")
    List<AccountDetail> findBusinessAccountDetails();
}