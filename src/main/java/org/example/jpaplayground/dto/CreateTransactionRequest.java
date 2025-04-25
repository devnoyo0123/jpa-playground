// 6.3 CreateTransactionRequest
package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {
    private Long accountId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String description;
}