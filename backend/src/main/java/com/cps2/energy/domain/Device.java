package com.cps2.energy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Device {

    private final UUID id;
    private final String name;
    private final String type;
    private final String status;
    private final String location;
    private final Boolean active;
    private final UUID userId;
    private final LocalDateTime createdAt;

    public Device(UUID id, String name, String type, String status, String location, Boolean active, UUID userId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.location = location;
        this.active = active;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public static Device newDevice(String name, String type, String location, UUID userId) {
        return new Device(UUID.randomUUID(), name, type, "OFF", location, true, userId, LocalDateTime.now());
    }

    public Device updateInfo(String name, String type, String status, String location) {
        return new Device(this.id, name, type, status, location, this.active, this.userId, this.createdAt);
    }

    public Device deactivate() {
        return new Device(this.id, this.name, this.type, this.status, this.location, false, this.userId, this.createdAt);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public Boolean getActive() {
        return active;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
