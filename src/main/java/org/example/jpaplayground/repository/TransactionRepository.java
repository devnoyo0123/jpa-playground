// 5. TransactionRepository 인터페이스
package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.Transaction;
import org.example.jpaplayground.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    List<Transaction> findByAmountGreaterThan(BigDecimal amount);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.account WHERE t.id = :id")
    Transaction findByIdWithAccount(@Param("id") Long id);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = :accountId")
    Long countTransactionsByAccountId(@Param("accountId") Long accountId);
}