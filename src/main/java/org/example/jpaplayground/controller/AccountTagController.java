// 2. AccountTagController 클래스
package org.example.jpaplayground.controller;

import org.example.jpaplayground.dto.AccountTagByNameRequest;
import org.example.jpaplayground.dto.AccountTagDto;
import org.example.jpaplayground.dto.AccountTagRequest;
import org.example.jpaplayground.service.AccountTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/account-tags")
@RequiredArgsConstructor
public class AccountTagController {

    private final AccountTagService accountTagService;

    @PostMapping
    public ResponseEntity<AccountTagDto> addTagToAccount(@RequestBody AccountTagRequest request) {
        AccountTagDto createdAccountTag = accountTagService.addTagToAccount(request);
        return new ResponseEntity<>(createdAccountTag, HttpStatus.CREATED);
    }

    @PostMapping("/by-name")
    public ResponseEntity<AccountTagDto> addTagToAccountByName(@RequestBody AccountTagByNameRequest request) {
        AccountTagDto createdAccountTag = accountTagService.addTagToAccountByName(request);
        return new ResponseEntity<>(createdAccountTag, HttpStatus.CREATED);
    }

    @DeleteMapping("/{accountId}/tags/{tagId}")
    public ResponseEntity<Void> removeTagFromAccount(
            @PathVariable Long accountId,
            @PathVariable Long tagId) {
        accountTagService.removeTagFromAccount(accountId, tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<AccountTagDto>> getAccountTagsByAccountId(@PathVariable Long accountId) {
        List<AccountTagDto> accountTags = accountTagService.getAccountTagsByAccountId(accountId);
        return ResponseEntity.ok(accountTags);
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<AccountTagDto>> getAccountTagsByTagId(@PathVariable Long tagId) {
        List<AccountTagDto> accountTags = accountTagService.getAccountTagsByTagId(tagId);
        return ResponseEntity.ok(accountTags);
    }

    @GetMapping("/added-by/{userId}")
    public ResponseEntity<List<AccountTagDto>> getAccountTagsByAddedBy(@PathVariable Long userId) {
        List<AccountTagDto> accountTags = accountTagService.getAccountTagsByAddedBy(userId);
        return ResponseEntity.ok(accountTags);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<AccountTagDto>> getAccountTagsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AccountTagDto> accountTags = accountTagService.getAccountTagsByDateRange(startDate, endDate);
        return ResponseEntity.ok(accountTags);
    }

    @DeleteMapping("/account/{accountId}/tags")
    public ResponseEntity<Void> removeAllTagsFromAccount(@PathVariable Long accountId) {
        accountTagService.removeAllTagsFromAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tag/{tagId}/accounts")
    public ResponseEntity<Void> removeAllAccountsFromTag(@PathVariable Long tagId) {
        accountTagService.removeAllAccountsFromTag(tagId);
        return ResponseEntity.noContent().build();
    }
}