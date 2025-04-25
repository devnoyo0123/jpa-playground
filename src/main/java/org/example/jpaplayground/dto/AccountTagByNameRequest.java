package org.example.jpaplayground.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTagByNameRequest {
    private Long accountId;
    private String tagName;
    private Long addedBy;
}