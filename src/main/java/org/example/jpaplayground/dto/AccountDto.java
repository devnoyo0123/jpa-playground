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
public class AccountDto {
    private Long id;
    private String accountName;
    private AccountType accountType;
    private AccountStatus status;
    private CountryCode countryCode;
    private LocaleCode localeCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userName;
    private AccountDetailDto accountDetail;
    private List<TransactionDto> recentTransactions;
    private List<TagSummaryDto> tags;

    public static AccountDto from(Account account) {
        AccountDtoBuilder builder = AccountDto.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .countryCode(account.getRegion().getCountryCode())
                .localeCode(account.getRegion().getLocaleCode())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt());

        // User 정보 추가 (null 체크)
        if (account.getUser() != null) {
            builder.userId(account.getUser().getId());
            builder.userName(account.getUser().getName());
        }

        // AccountDetail 정보 추가 (null 체크)
        if (account.getAccountDetail() != null) {
            builder.accountDetail(AccountDetailDto.from(account.getAccountDetail()));
        }

        // 최근 5개 거래내역 추가
        if (account.getTransactions() != null && !account.getTransactions().isEmpty()) {
            List<TransactionDto> transactions = account.getTransactions().stream()
                    .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                    .limit(5)
                    .map(TransactionDto::from)
                    .collect(Collectors.toList());
            builder.recentTransactions(transactions);
        }

        // 계정에 연결된 태그 정보 추가
        if (account.getAccountTags() != null && !account.getAccountTags().isEmpty()) {
            List<TagSummaryDto> tags = account.getAccountTags().stream()
                    .map(at -> TagSummaryDto.from(at.getTag()))
                    .collect(Collectors.toList());
            builder.tags(tags);
        }

        return builder.build();
    }
}