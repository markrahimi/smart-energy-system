package com.cps2.energy.web;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.User;

public record UserRepresentation(UUID id, String username, String email, String fullName, String role, LocalDateTime createdAt) {

    public static UserRepresentation fromDomain(User user) {
        return new UserRepresentation(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getRole(), user.getCreatedAt());
    }
}
