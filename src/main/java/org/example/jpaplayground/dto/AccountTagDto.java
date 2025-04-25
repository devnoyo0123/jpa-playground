// 3. AccountTagDto 클래스
package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.AccountTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTagDto {
    private Long id;
    private Long accountId;
    private Long tagId;
    private String tagName;
    private LocalDateTime taggedDate;
    private Long addedBy;

    public static AccountTagDto from(AccountTag accountTag) {
        return AccountTagDto.builder()
                .id(accountTag.getId())
                .accountId(accountTag.getAccount().getId())
                .tagId(accountTag.getTag().getId())
                .tagName(accountTag.getTag().getName())
                .taggedDate(accountTag.getTaggedDate())
                .addedBy(accountTag.getAddedBy())
                .build();
    }
}