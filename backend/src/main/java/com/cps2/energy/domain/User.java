package com.cps2.energy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String username;
    private final String password;
    private final String email;
    private final String fullName;
    private final String role;
    private final LocalDateTime createdAt;

    public User(UUID id, String username, String password, String email, String fullName, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static User newUser(String username, String password, String email, String fullName, String role) {
        return new User(UUID.randomUUID(), username, password, email, fullName, role != null ? role : "USER", LocalDateTime.now());
    }

    public User updateInfo(String username, String email, String fullName, String role) {
        return new User(this.id, username, this.password, email, fullName, role, this.createdAt);
    }

    public User updatePassword(String newPassword) {
        return new User(this.id, this.username, newPassword, this.email, this.fullName, this.role, this.createdAt);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
