package com.cps2.energy.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.Device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "active", nullable = false)
    private Boolean active;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public static DeviceEntity fromDomain(Device device) {
        DeviceEntity entity = new DeviceEntity();
        entity.setId(device.getId());
        entity.setName(device.getName());
        entity.setType(device.getType());
        entity.setStatus(device.getStatus());
        entity.setLocation(device.getLocation());
        entity.setActive(device.getActive());
        entity.setUserId(device.getUserId());
        entity.setCreatedAt(device.getCreatedAt());
        return entity;
    }

    public Device toDomain() {
        return new Device(id, name, type, status, location, active, userId, createdAt);
    }
}