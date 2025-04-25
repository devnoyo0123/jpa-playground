
// 3. TransactionController 클래스
package org.example.jpaplayground.controller;

import org.example.jpaplayground.domain.TransactionType;
import org.example.jpaplayground.dto.CreateTransactionRequest;
import org.example.jpaplayground.dto.TransactionDto;
import org.example.jpaplayground.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody CreateTransactionRequest request) {
        TransactionDto createdTransaction = transactionService.createTransaction(request);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long transactionId) {
        TransactionDto transaction = transactionService.getTransaction(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccount(@PathVariable Long accountId) {
        List<TransactionDto> transactions = transactionService.getTransactionsByAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/type/{transactionType}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByType(
            @PathVariable TransactionType transactionType) {
        List<TransactionDto> transactions = transactionService.getTransactionsByType(transactionType);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDto>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDto> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/large-transactions")
    public ResponseEntity<List<TransactionDto>> getLargeTransactions(@RequestParam BigDecimal threshold) {
        List<TransactionDto> largeTransactions = transactionService.getLargeTransactions(threshold);
        return ResponseEntity.ok(largeTransactions);
    }

    @GetMapping("/count/{accountId}")
    public ResponseEntity<Long> getTransactionCount(@PathVariable Long accountId) {
        Long count = transactionService.getTransactionCount(accountId);
        return ResponseEntity.ok(count);
    }
}
