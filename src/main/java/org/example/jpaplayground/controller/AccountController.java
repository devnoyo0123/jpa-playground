package org.example.jpaplayground.controller;

import org.example.jpaplayground.domain.AccountStatus;
import org.example.jpaplayground.domain.AccountType;
import org.example.jpaplayground.domain.CountryCode;
import org.example.jpaplayground.dto.*;
import org.example.jpaplayground.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request) {
        AccountDto createdAccount = accountService.createAccount(request);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId) {
        AccountDto account = accountService.getAccount(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/type/{accountType}")
    public ResponseEntity<List<AccountDto>> getAccountsByType(@PathVariable AccountType accountType) {
        List<AccountDto> accounts = accountService.getAccountsByType(accountType);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<List<AccountDto>> getAccountsByCountryCode(@PathVariable CountryCode countryCode) {
        List<AccountDto> accounts = accountService.getAccountsByCountryCode(countryCode);
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountId}/name")
    public ResponseEntity<AccountDto> updateAccountName(@PathVariable Long accountId, @RequestParam String newName) {
        AccountDto updatedAccount = accountService.updateAccountName(accountId, newName);
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<AccountDto>> getDeletedAccounts() {
        List<AccountDto> deletedAccounts = accountService.getDeletedAccounts();
        return ResponseEntity.ok(deletedAccounts);
    }

    // 새로 추가된 메서드들 (관계 맵핑 기능)

    // Account와 User 관계 메서드 (Many-to-One)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable Long userId) {
        List<AccountDto> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    // Account와 AccountDetail 관계 메서드 (One-to-One)
    @GetMapping("/{accountId}/detail")
    public ResponseEntity<AccountDetailDto> getAccountDetailByAccountId(@PathVariable Long accountId) {
        AccountDetailDto detail = accountService.getAccountDetailByAccountId(accountId);
        return ResponseEntity.ok(detail);
    }

    // Account와 Transaction 관계 메서드 (One-to-Many)
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionDto> transactions = accountService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    // 계정 상태 변경 메서드들
    @PutMapping("/{accountId}/status")
    public ResponseEntity<AccountDto> changeAccountStatus(
            @PathVariable Long accountId,
            @RequestParam AccountStatus status) {
        AccountDto updatedAccount = accountService.changeAccountStatus(accountId, status);
        return ResponseEntity.ok(updatedAccount);
    }

    @PutMapping("/{accountId}/activate")
    public ResponseEntity<AccountDto> activateAccount(@PathVariable Long accountId) {
        AccountDto activatedAccount = accountService.activateAccount(accountId);
        return ResponseEntity.ok(activatedAccount);
    }

    @PutMapping("/{accountId}/deactivate")
    public ResponseEntity<AccountDto> deactivateAccount(@PathVariable Long accountId) {
        AccountDto deactivatedAccount = accountService.deactivateAccount(accountId);
        return ResponseEntity.ok(deactivatedAccount);
    }

    @PutMapping("/{accountId}/suspend")
    public ResponseEntity<AccountDto> suspendAccount(@PathVariable Long accountId) {
        AccountDto suspendedAccount = accountService.suspendAccount(accountId);
        return ResponseEntity.ok(suspendedAccount);
    }

    // 태그 관련 메서드
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<AccountDto>> getAccountsByTagId(@PathVariable Long tagId) {
        List<AccountDto> accounts = accountService.getAccountsByTagId(tagId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/tag-name/{tagName}")
    public ResponseEntity<List<AccountDto>> getAccountsByTagName(@PathVariable String tagName) {
        List<AccountDto> accounts = accountService.getAccountsByTagName(tagName);
        return ResponseEntity.ok(accounts);
    }
}