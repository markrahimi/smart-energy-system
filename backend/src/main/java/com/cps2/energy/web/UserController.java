package com.cps2.energy.web;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cps2.energy.application.UserService;
import com.cps2.energy.domain.User;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<UserRepresentation> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserRepresentation::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<UserRepresentation> getUserById(@PathVariable UUID id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(UserRepresentation.fromDomain(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username/{username}")
    ResponseEntity<UserRepresentation> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(UserRepresentation.fromDomain(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<UserRepresentation> createUser(@RequestBody UserToCreateRepresentation userToCreate) {
        try {
            User user = User.newUser(
                    userToCreate.username(),
                    userToCreate.password(),
                    userToCreate.email(),
                    userToCreate.fullName(),
                    userToCreate.role()
            );
            User createdUser = userService.createUser(user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdUser.getId())
                    .toUri();

            return ResponseEntity.created(location).body(UserRepresentation.fromDomain(createdUser));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<UserRepresentation> updateUser(@PathVariable UUID id, @RequestBody UserToUpdateRepresentation userToUpdate) {
        try {
            User existingUser = userService.getUserById(id);
            User updatedUser = existingUser.updateInfo(
                    userToUpdate.username(),
                    userToUpdate.email(),
                    userToUpdate.fullName(),
                    userToUpdate.role()
            );
            User savedUser = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(UserRepresentation.fromDomain(savedUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
