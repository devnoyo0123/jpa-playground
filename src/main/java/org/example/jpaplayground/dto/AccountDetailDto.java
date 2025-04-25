
// 2. AccountDetailDto 클래스
package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.AccountDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetailDto {
    private Long accountId;
    private BigDecimal balance;
    private Integer creditScore;
    private String description;
    private Boolean isActive;

    public static AccountDetailDto from(AccountDetail detail) {
        return AccountDetailDto.builder()
                .accountId(detail.getId())
                .balance(detail.getBalance())
                .creditScore(detail.getCreditScore())
                .description(detail.getDescription())
                .isActive(detail.getIsActive())
                .build();
    }
}