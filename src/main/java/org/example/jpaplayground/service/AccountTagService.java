// 2. AccountTagService 클래스
package org.example.jpaplayground.service;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.AccountTag;
import org.example.jpaplayground.domain.Tag;
import org.example.jpaplayground.dto.AccountDto;
import org.example.jpaplayground.dto.AccountTagByNameRequest;
import org.example.jpaplayground.dto.AccountTagDto;
import org.example.jpaplayground.dto.AccountTagRequest;
import org.example.jpaplayground.repository.AccountRepository;
import org.example.jpaplayground.repository.AccountTagRepository;
import org.example.jpaplayground.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountTagService {

    private final AccountTagRepository accountTagRepository;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;

    @Transactional
    public AccountTagDto addTagToAccount(AccountTagRequest request) {
        // 이미 연결되어 있는지 확인
        if (accountTagRepository.findByAccountIdAndTagId(request.getAccountId(), request.getTagId()).isPresent()) {
            throw new EntityExistsException("This tag is already associated with the account");
        }

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.getAccountId()));

        Tag tag = tagRepository.findById(request.getTagId())
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + request.getTagId()));

        // 연관관계 설정 메서드를 통한 엔티티 생성
        AccountTag accountTag = AccountTag.createAccountTag(account, tag, request.getAddedBy());

        AccountTag savedAccountTag = accountTagRepository.save(accountTag);
        return AccountTagDto.from(savedAccountTag);
    }

    @Transactional
    public AccountTagDto addTagToAccountByName(AccountTagByNameRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.getAccountId()));

        // 태그 이름으로 태그 검색, 없으면 새로 생성
        Tag tag = tagRepository.findByName(request.getTagName())
                .orElseGet(() -> tagRepository.save(Tag.builder()
                        .name(request.getTagName())
                        .description("Auto-generated tag")
                        .build()));

        // 이미 연결되어 있는지 확인
        if (accountTagRepository.findByAccountIdAndTagId(account.getId(), tag.getId()).isPresent()) {
            throw new EntityExistsException("This tag is already associated with the account");
        }

        // 연관관계 설정 메서드를 통한 엔티티 생성
        AccountTag accountTag = AccountTag.createAccountTag(account, tag, request.getAddedBy());

        AccountTag savedAccountTag = accountTagRepository.save(accountTag);
        return AccountTagDto.from(savedAccountTag);
    }

    @Transactional
    public void removeTagFromAccount(Long accountId, Long tagId) {
        AccountTag accountTag = accountTagRepository.findByAccountIdAndTagId(accountId, tagId)
                .orElseThrow(() -> new EntityNotFoundException("Account-Tag relationship not found"));

        Account account = accountTag.getAccount();
        Tag tag = accountTag.getTag();

        // 양방향 연관관계 제거
        account.removeAccountTag(accountTag);
        tag.removeAccountTag(accountTag);

        accountTagRepository.delete(accountTag);
    }

    public List<AccountTagDto> getAccountTagsByAccountId(Long accountId) {
        return accountTagRepository.findByAccountId(accountId).stream()
                .map(AccountTagDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountTagDto> getAccountTagsByTagId(Long tagId) {
        return accountTagRepository.findByTagId(tagId).stream()
                .map(AccountTagDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountTagDto> getAccountTagsByAddedBy(Long userId) {
        return accountTagRepository.findByAddedBy(userId).stream()
                .map(AccountTagDto::from)
                .collect(Collectors.toList());
    }

    public List<AccountTagDto> getAccountTagsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return accountTagRepository.findByTaggedDateBetween(startDate, endDate).stream()
                .map(AccountTagDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeAllTagsFromAccount(Long accountId) {
        accountTagRepository.deleteAllByAccountId(accountId);
    }

    @Transactional
    public void removeAllAccountsFromTag(Long tagId) {
        accountTagRepository.deleteAllByTagId(tagId);
    }
}