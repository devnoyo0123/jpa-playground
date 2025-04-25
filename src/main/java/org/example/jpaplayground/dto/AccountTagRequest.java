package org.example.jpaplayground.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTagRequest {
    private Long accountId;
    private Long tagId;
    private Long addedBy;
}