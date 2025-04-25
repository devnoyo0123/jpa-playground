// 1. AccountJoinFetchController - JOIN FETCH 기능을 사용하는 계정 컨트롤러
package org.example.jpaplayground.controller;

import org.example.jpaplayground.domain.AccountType;
import org.example.jpaplayground.dto.AccountDetailedDto;
import org.example.jpaplayground.dto.AccountDto;
import org.example.jpaplayground.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/fetch")
@RequiredArgsConstructor
public class AccountJoinFetchController {

    private final AccountService accountService;

    // 계정과 사용자 정보를 함께 조회
    @GetMapping("/{accountId}/with-user")
    public ResponseEntity<AccountDto> getAccountWithUser(@PathVariable Long accountId) {
        AccountDto account = accountService.getAccountWithUser(accountId);
        return ResponseEntity.ok(account);
    }

    // 계정과 거래내역을 함께 조회
    @GetMapping("/{accountId}/with-transactions")
    public ResponseEntity<AccountDto> getAccountWithTransactions(@PathVariable Long accountId) {
        AccountDto account = accountService.getAccountWithTransactions(accountId);
        return ResponseEntity.ok(account);
    }

    // 계정과 상세정보를 함께 조회
    @GetMapping("/{accountId}/with-detail")
    public ResponseEntity<AccountDto> getAccountWithDetail(@PathVariable Long accountId) {
        AccountDto account = accountService.getAccountWithDetail(accountId);
        return ResponseEntity.ok(account);
    }

    // 계정과 태그 정보를 함께 조회
    @GetMapping("/{accountId}/with-tags")
    public ResponseEntity<AccountDto> getAccountWithTags(@PathVariable Long accountId) {
        AccountDto account = accountService.getAccountWithTags(accountId);
        return ResponseEntity.ok(account);
    }

    // 계정의 모든 연관 관계를 함께 조회 (상세 정보용)
    @GetMapping("/{accountId}/all-relations")
    public ResponseEntity<AccountDetailedDto> getAccountWithAllRelations(@PathVariable Long accountId) {
        AccountDetailedDto account = accountService.getAccountWithAllRelations(accountId);
        return ResponseEntity.ok(account);
    }

    // 사용자 ID로 계정과 상세정보 함께 조회
    @GetMapping("/user/{userId}/with-detail")
    public ResponseEntity<List<AccountDto>> getAccountsByUserIdWithDetail(@PathVariable Long userId) {
        List<AccountDto> accounts = accountService.getAccountsByUserIdWithDetail(userId);
        return ResponseEntity.ok(accounts);
    }

    // 계정 타입별로 계정과 사용자 정보 함께 조회
    @GetMapping("/type/{accountType}/with-user")
    public ResponseEntity<List<AccountDto>> getAccountsByTypeWithUser(@PathVariable AccountType accountType) {
        List<AccountDto> accounts = accountService.getAccountsByTypeWithUser(accountType);
        return ResponseEntity.ok(accounts);
    }

    // 활성화된 계정과 상세정보 함께 조회
    @GetMapping("/active/with-detail")
    public ResponseEntity<List<AccountDto>> getActiveAccountsWithDetail() {
        List<AccountDto> accounts = accountService.getActiveAccountsWithDetail();
        return ResponseEntity.ok(accounts);
    }

    // 특정 태그를 가진 계정과 사용자 정보 함께 조회
    @GetMapping("/tag/{tagId}/with-user")
    public ResponseEntity<List<AccountDto>> getAccountsByTagIdWithUser(@PathVariable Long tagId) {
        List<AccountDto> accounts = accountService.getAccountsByTagIdWithUser(tagId);
        return ResponseEntity.ok(accounts);
    }
}
