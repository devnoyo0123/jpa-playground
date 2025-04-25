package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    private String accountName;
    private Long userId;
    private AccountType accountType;
}