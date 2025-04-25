package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.AccountType;
import org.example.jpaplayground.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    Optional<Account> findByAccountName(String accountName);

    List<Account> findByAccountType(AccountType accountType);

    // JPQL을 이용한 쿼리 메서드
    @Query("SELECT a FROM Account a WHERE a.region.countryCode = :countryCode")
    List<Account> findByCountryCode(@Param("countryCode") String countryCode);

    // 특정 태그가 있는 모든 계정 조회
    @Query("SELECT a FROM Account a JOIN a.accountTags at WHERE at.tag.id = :tagId")
    List<Account> findAccountsByTagId(@Param("tagId") Long tagId);

    // 특정 태그 이름으로 계정 조회
    @Query("SELECT a FROM Account a JOIN a.accountTags at JOIN at.tag t WHERE t.name = :tagName")
    List<Account> findAccountsByTagName(@Param("tagName") String tagName);

    // JOIN FETCH를 사용하여 Account와 User 함께 조회
    @Query("SELECT a FROM Account a JOIN FETCH a.user WHERE a.id = :accountId")
    Optional<Account> findByIdWithUser(@Param("accountId") Long accountId);

    // JOIN FETCH로 특정 사용자의 모든 계정과 계정 상세 정보 함께 조회
    @Query("SELECT a FROM Account a JOIN FETCH a.user u LEFT JOIN FETCH a.accountDetail WHERE u.id = :userId")
    List<Account> findByUserIdWithDetail(@Param("userId") Long userId);

    // 계정 타입별로 계정과 사용자 정보 함께 조회
    @Query("SELECT a FROM Account a JOIN FETCH a.user WHERE a.accountType = :accountType")
    List<Account> findByAccountTypeWithUser(@Param("accountType") AccountType accountType);

    // JOIN FETCH를 사용하여 Account와 모든 Transaction 함께 조회
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.transactions WHERE a.id = :accountId")
    Optional<Account> findByIdWithTransactions(@Param("accountId") Long accountId);

    // JOIN FETCH를 사용하여 Account, User, 그리고 모든 Transaction 함께 조회
    @Query("SELECT a FROM Account a JOIN FETCH a.user LEFT JOIN FETCH a.transactions WHERE a.id = :accountId")
    Optional<Account> findByIdWithUserAndTransactions(@Param("accountId") Long accountId);

    // JOIN FETCH를 사용하여 Account와 AccountDetail 함께 조회
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.accountDetail WHERE a.id = :accountId")
    Optional<Account> findByIdWithDetail(@Param("accountId") Long accountId);

    // 활성화된 계정과 계정 상세 정보 함께 조회
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.accountDetail ad WHERE ad.isActive = true")
    List<Account> findActiveAccountsWithDetail();

    // JOIN FETCH를 사용하여 Account와 관련된 모든 Tag 함께 조회
    @Query("SELECT DISTINCT a FROM Account a LEFT JOIN FETCH a.accountTags at LEFT JOIN FETCH at.tag WHERE a.id = :accountId")
    Optional<Account> findByIdWithTags(@Param("accountId") Long accountId);

    // 특정 태그를 가진 모든 계정과 사용자 정보 함께 조회
    @Query("SELECT DISTINCT a FROM Account a JOIN FETCH a.user JOIN a.accountTags at JOIN at.tag t WHERE t.id = :tagId")
    List<Account> findByTagIdWithUser(@Param("tagId") Long tagId);

    // JOIN FETCH를 사용하여 Account와 모든 연관 엔티티 함께 조회
    @Query("SELECT DISTINCT a FROM Account a " +
            "JOIN FETCH a.user " +
            "LEFT JOIN FETCH a.accountDetail " +
            "WHERE a.id = :accountId")
    Optional<Account> findByIdWithAllRelations(@Param("accountId") Long accountId);

    // 사용자와 모든 계정 함께 조회
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.accounts WHERE u.id = :userId")
    Optional<User> findByIdWithAccounts(@Param("userId") Long userId);

    // 사용자, 계정, 계정 상세 정보 모두 함께 조회
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.accounts a LEFT JOIN FETCH a.accountDetail WHERE u.id = :userId")
    Optional<User> findByIdWithAccountsAndDetails(@Param("userId") Long userId);

    // 계정이 있는 모든 사용자 조회
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.accounts")
    List<User> findAllWithAccounts();
}