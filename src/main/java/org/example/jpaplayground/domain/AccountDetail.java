// 4. One-to-One 관계: AccountDetail 엔티티
package org.example.jpaplayground.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDetail extends BaseEntity {

    @Id
    @Column(name = "account_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Builder
    public AccountDetail(Account account, BigDecimal balance, Integer creditScore, String description) {
        this.account = account;
        this.balance = balance;
        this.creditScore = creditScore;
        this.description = description;
        this.isActive = true;
    }

    public void updateBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }

    public void updateCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }
}