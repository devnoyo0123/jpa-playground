package org.example.jpaplayground.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Where(clause = "delete_yn = 'N'")
@Table(name = "account_info", uniqueConstraints = {
        @UniqueConstraint(name = "idx_account_info_u1", columnNames = {"account_nm"})
})
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(name = "account_nm", nullable = false)
    private String accountName;

    @Embedded
    private Region region;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 30, name = "account_type")
    private AccountType accountType;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, name = "account_stats_cd")
    private AccountStatus status;

    @Column(name = "cre_id", nullable = false)
    private Long createdId;

    @Column(name = "upd_id", nullable = false)
    private Long updatedId;

    // One-to-One 관계 추가
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountDetail accountDetail;

    // One-to-Many 관계 추가
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    // Many-to-One 관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Many-to-Many 관계 추가 (중간 테이블을 통한 구현)
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTag> accountTags = new ArrayList<>();

    @Builder
    private Account(String accountName, Long userId, AccountType accountType) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.region = Region.korea();
        this.status = AccountStatus.GEN;
        this.createdId = userId;
        this.updatedId = userId;
    }

    // 연관관계 편의 메서드 추가
    public void setUser(User user) {
        this.user = user;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    // 태그 관련 연관관계 편의 메서드
    public void addAccountTag(AccountTag accountTag) {
        this.accountTags.add(accountTag);
    }

    public void removeAccountTag(AccountTag accountTag) {
        this.accountTags.remove(accountTag);
    }

    public void updateAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void changeStatus(AccountStatus status) {
        this.status = status;
    }

    public void activate() {
        this.status = AccountStatus.ACT;
    }

    public void deactivate() {
        this.status = AccountStatus.INA;
    }

    public void suspend() {
        this.status = AccountStatus.SUS;
    }
}