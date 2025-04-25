// 6.2 CreateAccountDetailRequest
package org.example.jpaplayground.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDetailRequest {
    private Long accountId;
    private BigDecimal initialBalance;
    private Integer creditScore;
    private String description;
}