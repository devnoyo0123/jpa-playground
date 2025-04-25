// 2. AccountDetailService 클래스
package org.example.jpaplayground.service;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.AccountDetail;
import org.example.jpaplayground.dto.AccountDetailDto;
import org.example.jpaplayground.dto.CreateAccountDetailRequest;
import org.example.jpaplayground.dto.UpdateAccountDetailRequest;
import org.example.jpaplayground.repository.AccountDetailRepository;
import org.example.jpaplayground.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountDetailService {

    private final AccountDetailRepository accountDetailRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountDetailDto createAccountDetail(CreateAccountDetailRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.getAccountId()));

        AccountDetail accountDetail = AccountDetail.builder()
                .account(account)
                .balance(request.getInitialBalance())
                .creditScore(request.getCreditScore())
                .description(request.getDescription())
                .build();

        AccountDetail savedDetail = accountDetailRepository.save(accountDetail);
        account.setAccountDetail(savedDetail);

        return AccountDetailDto.from(savedDetail);
    }

    public AccountDetailDto getAccountDetail(Long accountId) {
        AccountDetail accountDetail = accountDetailRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account detail not found for account id: " + accountId));

        return AccountDetailDto.from(accountDetail);
    }

    @Transactional
    public AccountDetailDto updateAccountDetail(Long accountId, UpdateAccountDetailRequest request) {
        AccountDetail accountDetail = accountDetailRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account detail not found for account id: " + accountId));

        if (request.getBalance() != null) {
            accountDetail.updateBalance(request.getBalance());
        }

        if (request.getCreditScore() != null) {
            accountDetail.updateCreditScore(request.getCreditScore());
        }

        if (request.getDescription() != null) {
            accountDetail.updateDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            if (request.getIsActive()) {
                accountDetail.activate();
            } else {
                accountDetail.deactivate();
            }
        }

        return AccountDetailDto.from(accountDetail);
    }

    public List<AccountDetailDto> getAccountsWithBalanceGreaterThan(BigDecimal minBalance) {
        return accountDetailRepository.findByBalanceGreaterThan(minBalance).stream()
                .map(AccountDetailDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountDetailDto> getActiveAccounts() {
        return accountDetailRepository.findByIsActiveTrue().stream()
                .map(AccountDetailDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountDetailDto> getInactiveAccounts() {
        return accountDetailRepository.findByIsActiveFalse().stream()
                .map(AccountDetailDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountDetailDto> getBusinessAccountDetails() {
        return accountDetailRepository.findBusinessAccountDetails().stream()
                .map(AccountDetailDto::from)
                .collect(Collectors.toList());
    }
}
