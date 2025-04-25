// 4. UserDto 클래스
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
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AccountSummaryDto> accounts;

    public static UserDto from(User user) {
        UserDtoBuilder builder = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt());

        if (user.getAccounts() != null && !user.getAccounts().isEmpty()) {
            List<AccountSummaryDto> accountSummaries = user.getAccounts().stream()
                    .map(AccountSummaryDto::from)
                    .collect(Collectors.toList());
            builder.accounts(accountSummaries);
        }

        return builder.build();
    }
}
