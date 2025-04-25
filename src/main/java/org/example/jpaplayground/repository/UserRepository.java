package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByNameContaining(String name);

    // JOIN FETCH를 사용하여 사용자와 계정 함께 조회
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.accounts WHERE u.id = :userId")
    Optional<User> findByIdWithAccounts(@Param("userId") Long userId);

    // JOIN FETCH를 사용하여 사용자, 계정, 계정 상세 정보 함께 조회
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.accounts a LEFT JOIN FETCH a.accountDetail WHERE u.id = :userId")
    Optional<User> findByIdWithAccountsAndDetails(@Param("userId") Long userId);

    // 계정이 있는 모든 사용자 조회
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.accounts")
    List<User> findAllWithAccounts();

    // 사용자, 계정, 거래내역 함께 조회
//    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.accounts a LEFT JOIN FETCH a.transactions WHERE u.id = :userId")
//    Optional<User> findByIdWithAccountsAndTransactions(@Param("userId") Long userId);

    // 사용자, 계정, 태그 정보 함께 조회
//    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.accounts a LEFT JOIN FETCH a.accountTags at LEFT JOIN FETCH at.tag WHERE u.id = :userId")
//    Optional<User> findByIdWithAccountsAndTags(@Param("userId") Long userId);

    // 사용자와 모든 연관 정보 함께 조회 (복합 JOIN FETCH)
    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.accounts a " +
            "LEFT JOIN FETCH a.accountDetail " +
            "WHERE u.id = :userId")
    Optional<User> findByIdWithAllRelations(@Param("userId") Long userId);

    // 삭제되지 않은 사용자 조회
    @Query("SELECT u FROM User u WHERE u.deleteYn = 'N'")
    List<User> findActiveUsers();

    // 특정 계정 타입을 가진 사용자 조회
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.accounts a WHERE a.accountType = org.example.jpaplayground.domain.AccountType.BUSINESS")
    List<User> findUsersWithBusinessAccounts();

    // 특정 태그를 가진 계정의 사용자 조회
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.accounts a JOIN a.accountTags at JOIN at.tag t WHERE t.name = :tagName")
    List<User> findUsersByAccountTagName(@Param("tagName") String tagName);
}