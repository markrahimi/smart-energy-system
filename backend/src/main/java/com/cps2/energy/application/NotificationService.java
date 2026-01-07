package com.cps2.energy.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.Notification;
import com.cps2.energy.persistence.NotificationEntity;
import com.cps2.energy.persistence.NotificationRepository;
import com.cps2.energy.persistence.UserRepository;




@Service
@Transactional
public class NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;

    NotificationService(NotificationRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications(UUID userId, Boolean isRead, String type) {
        List<NotificationEntity> entities;
        if (userId != null && isRead != null) {
            entities = repository.findByUserIdAndIsRead(userId, isRead);
        } else if (userId != null && type != null) {
            entities = repository.findByUserIdAndType(userId, type);
        } else if (userId != null) {
            entities = repository.findByUserIdOrderByCreatedAtDesc(userId);
        } else {
            entities = repository.findAll();
        }
        return entities.stream().map(NotificationEntity::toDomain).toList();
    }

    public Notification getNotificationById(UUID id) {
        NotificationEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id));

        Notification notification = entity.toDomain();
        if (!notification.getIsRead()) {
            Notification readNotification = notification.markAsRead();
            repository.save(NotificationEntity.fromDomain(readNotification));
            return readNotification;
        }
        return notification;
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(UUID userId) {
        return repository.countByUserIdAndIsRead(userId, false);
    }

    public Notification createNotification(Notification notification) {
        userRepository.findById(notification.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + notification.getUserId()));

        NotificationEntity entity = NotificationEntity.fromDomain(notification);
        NotificationEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    public void markAllAsRead(UUID userId) {
        List<NotificationEntity> unreadNotifications = repository.findByUserIdAndIsRead(userId, false);
        for (NotificationEntity entity : unreadNotifications) {
            entity.setIsRead(true);
        }
        repository.saveAll(unreadNotifications);
    }
}
