package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagSummaryDto {
    private Long id;
    private String name;

    public static TagSummaryDto from(Tag tag) {
        return TagSummaryDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}