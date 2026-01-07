package com.cps2.energy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Threshold {
    private final UUID id;
    private final UUID deviceId;
    private final UUID userId;
    private final Double minTemperature;
    private final Double maxTemperature;
    private final Double minHumidity;
    private final Double maxHumidity;
    private final Double minLuminosity;
    private final Double maxLuminosity;
    private final Double minPower;
    private final Double maxPower;
    private final Double minVoltage;
    private final Double maxVoltage;
    private final Double minCurrent;
    private final Double maxCurrent;
    private final boolean active;
    private final LocalDateTime createdAt;

    public Threshold(UUID id, UUID deviceId, UUID userId,
            Double minTemperature, Double maxTemperature,
            Double minHumidity, Double maxHumidity,
            Double minLuminosity, Double maxLuminosity,
            Double minPower, Double maxPower,
            Double minVoltage, Double maxVoltage,
            Double minCurrent, Double maxCurrent,
            boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.minLuminosity = minLuminosity;
        this.maxLuminosity = maxLuminosity;
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.minCurrent = minCurrent;
        this.maxCurrent = maxCurrent;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static Threshold newThreshold(UUID deviceId, UUID userId,
            Double minTemperature, Double maxTemperature,
            Double minHumidity, Double maxHumidity,
            Double minLuminosity, Double maxLuminosity,
            Double minPower, Double maxPower,
            Double minVoltage, Double maxVoltage,
            Double minCurrent, Double maxCurrent) {
        return new Threshold(UUID.randomUUID(), deviceId, userId,
                minTemperature, maxTemperature,
                minHumidity, maxHumidity,
                minLuminosity, maxLuminosity,
                minPower, maxPower,
                minVoltage, maxVoltage,
                minCurrent, maxCurrent,
                true, LocalDateTime.now());
    }

    public Threshold activate() {
        return new Threshold(id, deviceId, userId,
                minTemperature, maxTemperature,
                minHumidity, maxHumidity,
                minLuminosity, maxLuminosity,
                minPower, maxPower,
                minVoltage, maxVoltage,
                minCurrent, maxCurrent,
                true, createdAt);
    }

    public Threshold deactivate() {
        return new Threshold(id, deviceId, userId,
                minTemperature, maxTemperature,
                minHumidity, maxHumidity,
                minLuminosity, maxLuminosity,
                minPower, maxPower,
                minVoltage, maxVoltage,
                minCurrent, maxCurrent,
                false, createdAt);
    }

    public UUID getId() {
        return id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Double getMinHumidity() {
        return minHumidity;
    }

    public Double getMaxHumidity() {
        return maxHumidity;
    }

    public Double getMinLuminosity() {
        return minLuminosity;
    }

    public Double getMaxLuminosity() {
        return maxLuminosity;
    }

    public Double getMinPower() {
        return minPower;
    }

    public Double getMaxPower() {
        return maxPower;
    }

    public Double getMinVoltage() {
        return minVoltage;
    }

    public Double getMaxVoltage() {
        return maxVoltage;
    }

    public Double getMinCurrent() {
        return minCurrent;
    }

    public Double getMaxCurrent() {
        return maxCurrent;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
