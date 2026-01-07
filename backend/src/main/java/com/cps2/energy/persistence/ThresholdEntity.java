package com.cps2.energy.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.Threshold;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "thresholds")
public class ThresholdEntity {

    @Id
    private UUID id;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "min_humidity")
    private Double minHumidity;

    @Column(name = "max_humidity")
    private Double maxHumidity;

    @Column(name = "min_luminosity")
    private Double minLuminosity;

    @Column(name = "max_luminosity")
    private Double maxLuminosity;

    @Column(name = "min_power")
    private Double minPower;

    @Column(name = "max_power")
    private Double maxPower;

    @Column(name = "min_voltage")
    private Double minVoltage;

    @Column(name = "max_voltage")
    private Double maxVoltage;

    @Column(name = "min_current")
    private Double minCurrent;

    @Column(name = "max_current")
    private Double maxCurrent;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static ThresholdEntity fromDomain(Threshold threshold) {
        ThresholdEntity entity = new ThresholdEntity();
        entity.setId(threshold.getId());
        entity.setDeviceId(threshold.getDeviceId());
        entity.setUserId(threshold.getUserId());
        entity.setMinTemperature(threshold.getMinTemperature());
        entity.setMaxTemperature(threshold.getMaxTemperature());
        entity.setMinHumidity(threshold.getMinHumidity());
        entity.setMaxHumidity(threshold.getMaxHumidity());
        entity.setMinLuminosity(threshold.getMinLuminosity());
        entity.setMaxLuminosity(threshold.getMaxLuminosity());
        entity.setMinPower(threshold.getMinPower());
        entity.setMaxPower(threshold.getMaxPower());
        entity.setMinVoltage(threshold.getMinVoltage());
        entity.setMaxVoltage(threshold.getMaxVoltage());
        entity.setMinCurrent(threshold.getMinCurrent());
        entity.setMaxCurrent(threshold.getMaxCurrent());
        entity.setActive(threshold.isActive());
        entity.setCreatedAt(threshold.getCreatedAt());
        return entity;
    }

    public Threshold toDomain() {
        return new Threshold(id, deviceId, userId,
                minTemperature, maxTemperature,
                minHumidity, maxHumidity,
                minLuminosity, maxLuminosity,
                minPower, maxPower,
                minVoltage, maxVoltage,
                minCurrent, maxCurrent,
                active, createdAt);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public Double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(Double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public Double getMinLuminosity() {
        return minLuminosity;
    }

    public void setMinLuminosity(Double minLuminosity) {
        this.minLuminosity = minLuminosity;
    }

    public Double getMaxLuminosity() {
        return maxLuminosity;
    }

    public void setMaxLuminosity(Double maxLuminosity) {
        this.maxLuminosity = maxLuminosity;
    }

    public Double getMinPower() {
        return minPower;
    }

    public void setMinPower(Double minPower) {
        this.minPower = minPower;
    }

    public Double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Double maxPower) {
        this.maxPower = maxPower;
    }

    public Double getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(Double minVoltage) {
        this.minVoltage = minVoltage;
    }

    public Double getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(Double maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public Double getMinCurrent() {
        return minCurrent;
    }

    public void setMinCurrent(Double minCurrent) {
        this.minCurrent = minCurrent;
    }

    public Double getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(Double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
