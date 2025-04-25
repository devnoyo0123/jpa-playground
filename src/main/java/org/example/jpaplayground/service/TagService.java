// 1. TagService 클래스
package org.example.jpaplayground.service;

import org.example.jpaplayground.domain.Account;
import org.example.jpaplayground.domain.Tag;
import org.example.jpaplayground.dto.CreateTagRequest;
import org.example.jpaplayground.dto.TagDto;
import org.example.jpaplayground.dto.UpdateTagRequest;
import org.example.jpaplayground.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public TagDto createTag(CreateTagRequest request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Tag savedTag = tagRepository.save(tag);
        return TagDto.from(savedTag);
    }

    public TagDto getTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));

        return TagDto.from(tag);
    }

    public TagDto getTagByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with name: " + name));

        return TagDto.from(tag);
    }

    public List<TagDto> searchTags(String keyword) {
        return tagRepository.findByNameContaining(keyword).stream()
                .map(TagDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TagDto updateTag(Long tagId, UpdateTagRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));

        if (request.getName() != null) {
            tag.updateName(request.getName());
        }

        if (request.getDescription() != null) {
            tag.updateDescription(request.getDescription());
        }

        return TagDto.from(tag);
    }

    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));

        tag.delete();
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagDto::simple) // 간단한 정보만 반환
                .collect(Collectors.toList());
    }

    public List<TagDto> getTagsByAccountId(Long accountId) {
        return tagRepository.findTagsByAccountId(accountId).stream()
                .map(TagDto::simple)
                .collect(Collectors.toList());
    }

    public List<TagDto> getTagsByAccount(Account account) {
        return tagRepository.findTagsByAccount(account).stream()
                .map(TagDto::simple)
                .collect(Collectors.toList());
    }

    public List<TagDto> getPopularTags() {
        return tagRepository.findPopularTags().stream()
                .map(TagDto::simple)
                .collect(Collectors.toList());
    }
}
