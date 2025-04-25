// 2. UserJoinFetchController - JOIN FETCH 기능을 사용하는 사용자 컨트롤러
package org.example.jpaplayground.controller;

import org.example.jpaplayground.dto.UserDetailedDto;
import org.example.jpaplayground.dto.UserDto;
import org.example.jpaplayground.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/fetch")
@RequiredArgsConstructor
public class UserJoinFetchController {

    private final UserService userService;

    // 사용자와 모든 계정 함께 조회
    @GetMapping("/{userId}/with-accounts")
    public ResponseEntity<UserDto> getUserWithAccounts(@PathVariable Long userId) {
        UserDto user = userService.getUserWithAccounts(userId);
        return ResponseEntity.ok(user);
    }

    // 사용자, 계정, 계정 상세 정보 모두 함께 조회
    @GetMapping("/{userId}/with-accounts-details")
    public ResponseEntity<UserDetailedDto> getUserWithAccountsAndDetails(@PathVariable Long userId) {
        UserDetailedDto user = userService.getUserWithAccountsAndDetails(userId);
        return ResponseEntity.ok(user);
    }

    // 계정이 있는 모든 사용자 조회
    @GetMapping("/with-accounts")
    public ResponseEntity<List<UserDto>> getAllUsersWithAccounts() {
        List<UserDto> users = userService.getAllUsersWithAccounts();
        return ResponseEntity.ok(users);
    }
}