// 1. TagController 클래스
package org.example.jpaplayground.controller;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.dto.CreateTagRequest;
import org.example.jpaplayground.dto.TagDto;
import org.example.jpaplayground.dto.UpdateTagRequest;
import org.example.jpaplayground.service.AccountService;
import org.example.jpaplayground.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody CreateTagRequest request) {
        TagDto createdTag = tagService.createTag(request);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagDto> getTag(@PathVariable Long tagId) {
        TagDto tag = tagService.getTag(tagId);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagDto> getTagByName(@PathVariable String name) {
        TagDto tag = tagService.getTagByName(name);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagDto>> searchTags(@RequestParam String keyword) {
        List<TagDto> tags = tagService.searchTags(keyword);
        return ResponseEntity.ok(tags);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long tagId, @RequestBody UpdateTagRequest request) {
        TagDto updatedTag = tagService.updateTag(tagId, request);
        return ResponseEntity.ok(updatedTag);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TagDto>> getTagsByAccountId(@PathVariable Long accountId) {
        // 먼저 Account 객체를 조회
        Account account = accountService.findById(accountId);

        // 찾은 Account 객체를 사용하여 태그 조회
        List<TagDto> tags = tagService.getTagsByAccount(account);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<TagDto>> getPopularTags() {
        List<TagDto> popularTags = tagService.getPopularTags();
        return ResponseEntity.ok(popularTags);
    }
}