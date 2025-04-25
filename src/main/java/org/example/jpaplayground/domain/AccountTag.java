// 2. 중간 테이블 엔티티 (Many-to-Many를 One-to-Many, Many-to-One으로 분해)
package org.example.jpaplayground.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_tag",
        uniqueConstraints = @UniqueConstraint(name = "uk_account_tag", columnNames = {"account_id", "tag_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(name = "tagged_date")
    private LocalDateTime taggedDate;

    @Column(name = "added_by")
    private Long addedBy;

    @Builder
    public AccountTag(Account account, Tag tag, Long addedBy) {
        this.account = account;
        this.tag = tag;
        this.taggedDate = LocalDateTime.now();
        this.addedBy = addedBy;
    }

    // 양방향 관계 설정을 위한 연관관계 편의 메서드
    public static AccountTag createAccountTag(Account account, Tag tag, Long addedBy) {
        AccountTag accountTag = new AccountTag(account, tag, addedBy);
        account.addAccountTag(accountTag);
        tag.addAccountTag(accountTag);
        return accountTag;
    }
}