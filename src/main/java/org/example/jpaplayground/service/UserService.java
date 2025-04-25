package org.example.jpaplayground.service;

import org.example.jpaplayground.domain.User;
import org.example.jpaplayground.dto.CreateUserRequest;
import org.example.jpaplayground.dto.UserDetailedDto;
import org.example.jpaplayground.dto.UserDto;
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
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .build();

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findByIdWithAccounts(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return UserDto.from(user);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        return UserDto.from(user);
    }

    public List<UserDto> getUsersByName(String name) {
        List<User> users = userRepository.findByNameContaining(name);
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(Long userId, CreateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.updateName(request.getName());
        user.updatePhone(request.getPhone());

        return UserDto.from(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.delete();
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    // 사용자와 모든 계정 함께 조회
    public UserDto getUserWithAccounts(Long userId) {
        User user = userRepository.findByIdWithAccounts(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return UserDto.from(user);
    }

    // 사용자, 계정, 계정 상세 정보 모두 함께 조회
    public UserDetailedDto getUserWithAccountsAndDetails(Long userId) {
        User user = userRepository.findByIdWithAccountsAndDetails(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return UserDetailedDto.fromWithDetails(user);
    }

    // 계정이 있는 모든 사용자 조회
    public List<UserDto> getAllUsersWithAccounts() {
        List<User> users = userRepository.findAllWithAccounts();

        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}