package com.cps2.energy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cps2.energy.entity.Notification;
import com.cps2.energy.repository.NotificationRepository;
import com.cps2.energy.repository.UserRepository;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationController(NotificationRepository notificationRepository,
                                  UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    
    @GetMapping
    public List<Notification> getAllNotifications(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam(required = false) String type) {

        if (userId != null && isRead != null) {

            return notificationRepository.findByUserIdAndIsRead(userId, isRead);
        } else if (userId != null && type != null) {
            return notificationRepository.findByUserIdAndType(userId, type);
        } else if (userId != null) {
            return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }
        return notificationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            Notification notif = notification.get();
            if(!notif.getIsRead()) {
                notif.setIsRead(true);
                notificationRepository.save(notif);
            }
            return ResponseEntity.ok(notif);
        }
        return ResponseEntity.notFound().build();
    }
    

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        long count = notificationRepository.countByUserIdAndIsRead(userId, false);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        if (notification.getUser() == null || notification.getUser().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!userRepository.existsById(notification.getUser().getId())) {
            return ResponseEntity.badRequest().build();
        }

        Notification newNotification = notificationRepository.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(newNotification);
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {

        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsRead(userId, false);

        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }

        return ResponseEntity.ok().build();
    }
}
