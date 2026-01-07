package com.cps2.energy.web;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cps2.energy.application.NotificationService;
import com.cps2.energy.domain.Notification;

@RestController
@RequestMapping("/api/notifications")
class NotificationController {

    private final NotificationService notificationService;

    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    List<NotificationRepresentation> getAllNotifications(@RequestParam(required = false) UUID userId,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam(required = false) String type) {
        return notificationService.getAllNotifications(userId, isRead, type).stream()
                .map(NotificationRepresentation::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<NotificationRepresentation> getNotificationById(@PathVariable UUID id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            return ResponseEntity.ok(NotificationRepresentation.fromDomain(notification));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/unread-count")
    long getUnreadCount(@RequestParam UUID userId) {
        return notificationService.getUnreadCount(userId);
    }

    @PostMapping
    ResponseEntity<NotificationRepresentation> createNotification(
            @RequestBody NotificationToCreateRepresentation notificationToCreate) {
        try {
            Notification notification;
            if (notificationToCreate.thresholdId() != null) {
                notification = Notification.newThresholdNotification(
                        notificationToCreate.title(),
                        notificationToCreate.message(),
                        notificationToCreate.type(),
                        notificationToCreate.priority(),
                        notificationToCreate.userId(),
                        notificationToCreate.thresholdId());
            } else {
                notification = Notification.newNotification(
                        notificationToCreate.title(),
                        notificationToCreate.message(),
                        notificationToCreate.type(),
                        notificationToCreate.priority(),
                        notificationToCreate.userId());
            }
            Notification createdNotification = notificationService.createNotification(notification);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdNotification.getId())
                    .toUri();

            return ResponseEntity.created(location).body(NotificationRepresentation.fromDomain(createdNotification));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/mark-all-read")
    ResponseEntity<Void> markAllAsRead(@RequestParam UUID userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }
}
