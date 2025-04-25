
// 3. TransactionService 클래스
package org.example.jpaplayground.service;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.AccountDetail;
import org.example.jpaplayground.domain.Transaction;
import org.example.jpaplayground.domain.TransactionType;
import org.example.jpaplayground.dto.CreateTransactionRequest;
import org.example.jpaplayground.dto.TransactionDto;
import org.example.jpaplayground.repository.AccountDetailRepository;
import org.example.jpaplayground.repository.AccountRepository;
import org.example.jpaplayground.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountDetailRepository accountDetailRepository;

    @Transactional
    public TransactionDto createTransaction(CreateTransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.getAccountId()));

        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .description(request.getDescription())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        account.addTransaction(savedTransaction);

        // 계좌 잔액 업데이트
        updateAccountBalance(account, request.getAmount(), request.getTransactionType());

        return TransactionDto.from(savedTransaction);
    }

    @Transactional
    private void updateAccountBalance(Account account, BigDecimal amount, TransactionType transactionType) {
        AccountDetail accountDetail = accountDetailRepository.findById(account.getId())
                .orElse(null);

        if (accountDetail != null) {
            BigDecimal currentBalance = accountDetail.getBalance();
            BigDecimal newBalance;

            switch (transactionType) {
                case DEPOSIT:
                case TRANSFER_IN:
                case INTEREST:
                    newBalance = currentBalance.add(amount);
                    break;
                case WITHDRAWAL:
                case TRANSFER_OUT:
                case FEE:
                    newBalance = currentBalance.subtract(amount);
                    break;
                default:
                    // 변경 없음
                    return;
            }

            accountDetail.updateBalance(newBalance);
        }
    }

    public TransactionDto getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + transactionId));

        return TransactionDto.from(transaction);
    }

    public List<TransactionDto> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByType(TransactionType transactionType) {
        return transactionRepository.findByTransactionType(transactionType).stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByDateRange(startDate, endDate).stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getLargeTransactions(BigDecimal threshold) {
        return transactionRepository.findByAmountGreaterThan(threshold).stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }

    public Long getTransactionCount(Long accountId) {
        return transactionRepository.countTransactionsByAccountId(accountId);
    }
} 