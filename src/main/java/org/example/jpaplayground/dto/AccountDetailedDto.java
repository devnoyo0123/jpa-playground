// 1. AccountDetailedDto - 계정의 모든 관계를 포함하는 상세 DTO
package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetailedDto {
    // 기본 계정 정보
    private Long id;
    private String accountName;
    private AccountType accountType;
    private AccountStatus status;
    private CountryCode countryCode;
    private LocaleCode localeCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 연관 관계 정보
    private UserSummaryDto user;
    private AccountDetailDto accountDetail;
    private List<TransactionDto> transactions;
    private List<TagSummaryDto> tags;

    public static AccountDetailedDto fromWithAllRelations(Account account) {
        AccountDetailedDtoBuilder builder = AccountDetailedDto.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .countryCode(account.getRegion().getCountryCode())
                .localeCode(account.getRegion().getLocaleCode())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt());

        // User 정보 추가
        if (account.getUser() != null) {
            builder.user(UserSummaryDto.from(account.getUser()));
        }

        // AccountDetail 정보 추가
        if (account.getAccountDetail() != null) {
            builder.accountDetail(AccountDetailDto.from(account.getAccountDetail()));
        }

        // Transaction 정보 추가
        if (account.getTransactions() != null && !account.getTransactions().isEmpty()) {
            List<TransactionDto> transactions = account.getTransactions().stream()
                    .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                    .map(TransactionDto::from)
                    .collect(Collectors.toList());
            builder.transactions(transactions);
        }

        // Tag 정보 추가
        if (account.getAccountTags() != null && !account.getAccountTags().isEmpty()) {
            List<TagSummaryDto> tags = account.getAccountTags().stream()
                    .map(at -> TagSummaryDto.from(at.getTag()))
                    .collect(Collectors.toList());
            builder.tags(tags);
        }

        return builder.build();
    }
}
