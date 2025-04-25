package org.example.jpaplayground.service;

import org.example.jpaplayground.domain.*;
import org.example.jpaplayground.dto.*;
import org.example.jpaplayground.repository.AccountDetailRepository;
import org.example.jpaplayground.repository.AccountRepository;
import org.example.jpaplayground.repository.TransactionRepository;
import org.example.jpaplayground.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountDetailRepository accountDetailRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public AccountDto createAccount(CreateAccountRequest request) {
        // 사용자 조회 (Many-to-One 관계 설정)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));

        Account account = Account.builder()
                .accountName(request.getAccountName())
                .userId(request.getUserId())
                .accountType(request.getAccountType())
                .build();

        Account savedAccount = accountRepository.save(account);

        // 양방향 관계 설정
        user.addAccount(savedAccount);

        return AccountDto.from(savedAccount);
    }

    public AccountDto getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDto.from(account);
    }

    public List<AccountDto> getAccountsByType(AccountType accountType) {
        return accountRepository.findByAccountType(accountType).stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountDto> getAccountsByCountryCode(CountryCode countryCode) {
        return accountRepository.findByCountryCodeUsingQueryDsl(countryCode).stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        account.delete();
    }

    @Transactional
    public AccountDto updateAccountName(Long accountId, String newName) {
        accountRepository.updateAccountName(accountId, newName);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDto.from(account);
    }

    public List<AccountDto> getDeletedAccounts() {
        return accountRepository.findDeletedAccounts().stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    // 추가된 관계 메서드들

    // User와 Account 관계 메서드 (Many-to-One)
    public List<AccountDto> getAccountsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return user.getAccounts().stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    // Account와 AccountDetail 관계 메서드 (One-to-One)
    public AccountDetailDto getAccountDetailByAccountId(Long accountId) {
        AccountDetail detail = accountDetailRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account detail not found for account id: " + accountId));

        return AccountDetailDto.from(detail);
    }

    // Account와 Transaction 관계 메서드 (One-to-Many)
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        return transactions.stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountDto changeAccountStatus(Long accountId, AccountStatus newStatus) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        account.changeStatus(newStatus);

        return AccountDto.from(account);
    }

    @Transactional
    public AccountDto activateAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        account.activate();

        return AccountDto.from(account);
    }

    @Transactional
    public AccountDto deactivateAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        account.deactivate();

        return AccountDto.from(account);
    }

    @Transactional
    public AccountDto suspendAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        account.suspend();

        return AccountDto.from(account);
    }

    // 태그 관련 메서드
    public List<AccountDto> getAccountsByTagId(Long tagId) {
        return accountRepository.findAccountsByTagId(tagId).stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountDto> getAccountsByTagName(String tagName) {
        return accountRepository.findAccountsByTagName(tagName).stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    // 계정과 사용자 정보를 함께 조회
    public AccountDto getAccountWithUser(Long accountId) {
        Account account = accountRepository.findByIdWithUser(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDto.from(account);
    }

    // 계정과 거래내역을 함께 조회
    public AccountDto getAccountWithTransactions(Long accountId) {
        Account account = accountRepository.findByIdWithTransactions(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDto.from(account);
    }

    // 계정과 상세정보를 함께 조회
    public AccountDto getAccountWithDetail(Long accountId) {
        Account account = accountRepository.findByIdWithDetail(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDto.from(account);
    }

    // 계정과 태그 정보를 함께 조회
    public AccountDto getAccountWithTags(Long accountId) {
        Account account = accountRepository.findByIdWithTags(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDto.from(account);
    }

    // 계정의 모든 연관 관계를 함께 조회 (상세 정보용)
    public AccountDetailedDto getAccountWithAllRelations(Long accountId) {
        Account account = accountRepository.findByIdWithAllRelations(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return AccountDetailedDto.fromWithAllRelations(account);
    }

    // 사용자 ID로 계정과 상세정보 함께 조회
    public List<AccountDto> getAccountsByUserIdWithDetail(Long userId) {
        List<Account> accounts = accountRepository.findByUserIdWithDetail(userId);

        return accounts.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    // 계정 타입별로 계정과 사용자 정보 함께 조회
    public List<AccountDto> getAccountsByTypeWithUser(AccountType accountType) {
        List<Account> accounts = accountRepository.findByAccountTypeWithUser(accountType);

        return accounts.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    // 활성화된 계정과 상세정보 함께 조회
    public List<AccountDto> getActiveAccountsWithDetail() {
        List<Account> accounts = accountRepository.findActiveAccountsWithDetail();

        return accounts.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    // 특정 태그를 가진 계정과 사용자 정보 함께 조회
    public List<AccountDto> getAccountsByTagIdWithUser(Long tagId) {
        List<Account> accounts = accountRepository.findByTagIdWithUser(tagId);

        return accounts.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));
    }
}