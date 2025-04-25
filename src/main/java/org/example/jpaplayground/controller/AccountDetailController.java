// 2. AccountDetailController 클래스
package org.example.jpaplayground.controller;

import org.example.jpaplayground.dto.AccountDetailDto;
import org.example.jpaplayground.dto.CreateAccountDetailRequest;
import org.example.jpaplayground.dto.UpdateAccountDetailRequest;
import org.example.jpaplayground.service.AccountDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account-details")
@RequiredArgsConstructor
public class AccountDetailController {

    private final AccountDetailService accountDetailService;

    @PostMapping
    public ResponseEntity<AccountDetailDto> createAccountDetail(@RequestBody CreateAccountDetailRequest request) {
        AccountDetailDto createdDetail = accountDetailService.createAccountDetail(request);
        return new ResponseEntity<>(createdDetail, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDetailDto> getAccountDetail(@PathVariable Long accountId) {
        AccountDetailDto accountDetail = accountDetailService.getAccountDetail(accountId);
        return ResponseEntity.ok(accountDetail);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDetailDto> updateAccountDetail(
            @PathVariable Long accountId,
            @RequestBody UpdateAccountDetailRequest request) {
        AccountDetailDto updatedDetail = accountDetailService.updateAccountDetail(accountId, request);
        return ResponseEntity.ok(updatedDetail);
    }

    @GetMapping("/balance-greater-than")
    public ResponseEntity<List<AccountDetailDto>> getAccountsWithBalanceGreaterThan(
            @RequestParam BigDecimal minBalance) {
        List<AccountDetailDto> accounts = accountDetailService.getAccountsWithBalanceGreaterThan(minBalance);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AccountDetailDto>> getActiveAccounts() {
        List<AccountDetailDto> activeAccounts = accountDetailService.getActiveAccounts();
        return ResponseEntity.ok(activeAccounts);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<AccountDetailDto>> getInactiveAccounts() {
        List<AccountDetailDto> inactiveAccounts = accountDetailService.getInactiveAccounts();
        return ResponseEntity.ok(inactiveAccounts);
    }

    @GetMapping("/business")
    public ResponseEntity<List<AccountDetailDto>> getBusinessAccountDetails() {
        List<AccountDetailDto> businessAccounts = accountDetailService.getBusinessAccountDetails();
        return ResponseEntity.ok(businessAccounts);
    }
}
