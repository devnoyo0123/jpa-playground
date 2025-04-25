

// 5. AccountSummaryDto 클래스 (UserDto에서 사용)
package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.AccountStatus;
import org.example.jpaplayground.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSummaryDto {
    private Long id;
    private String accountName;
    private AccountType accountType;
    private AccountStatus status;

    public static AccountSummaryDto from(Account account) {
        return AccountSummaryDto.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .build();
    }
}