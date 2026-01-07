package com.cps2.energy.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.Notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    private UUID id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Column(name = "priority", nullable = false, length = 20)
    private String priority;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static NotificationEntity fromDomain(Notification notification) {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(notification.getId());
        entity.setTitle(notification.getTitle());
        entity.setMessage(notification.getMessage());
        entity.setType(notification.getType());
        entity.setPriority(notification.getPriority());
        entity.setIsRead(notification.getIsRead());
        entity.setUserId(notification.getUserId());
        entity.setCreatedAt(notification.getCreatedAt());
        return entity;
    }

    public Notification toDomain() {
        return new Notification(id, title, message, type, priority, isRead, userId, createdAt);
    }
}
