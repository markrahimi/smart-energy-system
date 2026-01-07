package com.cps2.energy.web;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.Notification;

public record NotificationRepresentation(UUID id, String title, String message, String type, String priority, Boolean isRead, UUID userId, LocalDateTime createdAt) {

    public static NotificationRepresentation fromDomain(Notification notification) {
        return new NotificationRepresentation(notification.getId(), notification.getTitle(), notification.getMessage(), notification.getType(), notification.getPriority(), notification.getIsRead(), notification.getUserId(), notification.getCreatedAt());
    }
}
