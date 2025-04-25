// 2. UserDetailedDto - 사용자와 관련 계정 정보를 포함하는 상세 DTO
package org.example.jpaplayground.dto;

import org.example.jpaplayground.domain.User;
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
public class UserDetailedDto {
    // 기본 사용자 정보
    private Long id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 계정 정보 리스트
    private List<AccountDto> accounts;

    public static UserDetailedDto fromWithDetails(User user) {
        UserDetailedDtoBuilder builder = UserDetailedDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt());

        if (user.getAccounts() != null && !user.getAccounts().isEmpty()) {
            List<AccountDto> accountDtos = user.getAccounts().stream()
                    .map(AccountDto::from)
                    .collect(Collectors.toList());
            builder.accounts(accountDtos);
        }

        return builder.build();
    }
}