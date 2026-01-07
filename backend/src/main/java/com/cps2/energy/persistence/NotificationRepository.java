package com.cps2.energy.persistence;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    List<NotificationEntity> findByUserId(UUID userId);

    List<NotificationEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<NotificationEntity> findByUserIdAndIsRead(UUID userId, Boolean isRead);

    List<NotificationEntity> findByUserIdAndType(UUID userId, String type);

    long countByUserIdAndIsRead(UUID userId, Boolean isRead);
}
