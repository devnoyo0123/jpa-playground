package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.Tag;
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
public class TagDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AccountTagDto> accountTags;
    private Integer accountCount;

    public static TagDto from(Tag tag) {
        TagDtoBuilder builder = TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt());

        if (tag.getAccountTags() != null) {
            builder.accountTags(tag.getAccountTags().stream()
                    .map(AccountTagDto::from)
                    .collect(Collectors.toList()));

            builder.accountCount(tag.getAccountTags().size());
        }

        return builder.build();
    }

    // 간단한 태그 정보만 표시하는 DTO 변환 메서드
    public static TagDto simple(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }
}