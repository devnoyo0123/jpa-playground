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
public class UpdateAccountDetailRequest {
    private BigDecimal balance;
    private Integer creditScore;
    private String description;
    private Boolean isActive;
}