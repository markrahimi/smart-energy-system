package com.cps2.energy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private final UUID id;
    private final String title;
    private final String message;
    private final String type;
    private final String priority;
    private final Boolean isRead;
    private final UUID userId;
    private final UUID thresholdId;
    private final LocalDateTime createdAt;

    public Notification(UUID id, String title, String message, String type, String priority, Boolean isRead,
            UUID userId, UUID thresholdId, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
        this.isRead = isRead;
        this.userId = userId;
        this.thresholdId = thresholdId;
        this.createdAt = createdAt;
    }

    public static Notification newNotification(String title, String message, String type, String priority,
            UUID userId) {
        return new Notification(UUID.randomUUID(), title, message, type, priority != null ? priority : "MEDIUM", false,
                userId, null, LocalDateTime.now());
    }

    public static Notification newThresholdNotification(String title, String message, String type, String priority,
            UUID userId, UUID thresholdId) {
        return new Notification(UUID.randomUUID(), title, message, type, priority != null ? priority : "MEDIUM", false,
                userId, thresholdId, LocalDateTime.now());
    }

    public Notification markAsRead() {
        if (isRead) {
            throw new IllegalStateException("Notification is already read");
        }
        return new Notification(this.id, this.title, this.message, this.type, this.priority, true, this.userId,
                this.thresholdId, this.createdAt);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getThresholdId() {
        return thresholdId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
