// 1. TagRepository 인터페이스
package org.example.jpaplayground.repository;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    List<Tag> findByNameContaining(String name);

    // 특정 계정에 연결된 모든 태그 조회
    @Query("SELECT t FROM Tag t JOIN t.accountTags at WHERE at.account.id = :accountId")
    List<Tag> findTagsByAccountId(@Param("accountId") Long accountId);

    // 특정 계정에 연결된 모든 태그 조회 (Account 엔티티 사용)
    @Query("SELECT t FROM Tag t JOIN t.accountTags at WHERE at.account = :account")
    List<Tag> findTagsByAccount(@Param("account") Account account);

    // 태그가 연결된 계정 수 기준으로 인기 태그 조회 (페이징 처리)
    @Query("SELECT t, COUNT(at.id) as tagCount FROM Tag t LEFT JOIN t.accountTags at " +
            "GROUP BY t.id ORDER BY tagCount DESC")
    List<Tag> findPopularTags();
}
