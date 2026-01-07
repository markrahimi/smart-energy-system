package com.cps2.energy.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.User;
import com.cps2.energy.persistence.UserEntity;
import com.cps2.energy.persistence.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.findAll().stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return repository.findById(id)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    public User createUser(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Username already exists: " + user.getUsername());
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email already exists: " + user.getEmail());
        }
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    public User updateUser(UUID id, User userDetails) {
        UserEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        existingEntity.setUsername(userDetails.getUsername());
        existingEntity.setEmail(userDetails.getEmail());
        existingEntity.setFullName(userDetails.getFullName());
        existingEntity.setRole(userDetails.getRole());

        UserEntity updatedEntity = repository.save(existingEntity);
        return updatedEntity.toDomain();
    }
}
