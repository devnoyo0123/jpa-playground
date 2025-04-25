package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.AccountTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountTagRepository extends JpaRepository<AccountTag, Long> {

    // 계정 ID와 태그 ID로 AccountTag 조회
    Optional<AccountTag> findByAccountIdAndTagId(Long accountId, Long tagId);

    // 특정 계정과 연결된 모든 AccountTag 조회
    List<AccountTag> findByAccountId(Long accountId);

    // 특정 태그와 연결된 모든 AccountTag 조회
    List<AccountTag> findByTagId(Long tagId);

    // 특정 사용자가 추가한 모든 AccountTag 조회
    List<AccountTag> findByAddedBy(Long userId);

    // 특정 기간 동안 추가된 AccountTag 조회
    @Query("SELECT at FROM AccountTag at WHERE at.taggedDate BETWEEN :startDate AND :endDate")
    List<AccountTag> findByTaggedDateBetween(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    // 특정 계정과 태그의 연결 삭제
    @Modifying
    @Query("DELETE FROM AccountTag at WHERE at.account.id = :accountId AND at.tag.id = :tagId")
    void deleteByAccountIdAndTagId(@Param("accountId") Long accountId, @Param("tagId") Long tagId);

    // 특정 계정과 연결된 모든 태그 관계 삭제
    @Modifying
    @Query("DELETE FROM AccountTag at WHERE at.account.id = :accountId")
    void deleteAllByAccountId(@Param("accountId") Long accountId);

    // 특정 태그와 연결된 모든 계정 관계 삭제
    @Modifying
    @Query("DELETE FROM AccountTag at WHERE at.tag.id = :tagId")
    void deleteAllByTagId(@Param("tagId") Long tagId);
}