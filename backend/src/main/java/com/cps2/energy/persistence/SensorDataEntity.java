package com.cps2.energy.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.SensorData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_data")
public class SensorDataEntity {

    @Id
    private UUID id;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "luminosity")
    private Double luminosity;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(Double luminosity) {
        this.luminosity = luminosity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static SensorDataEntity fromDomain(SensorData data) {
        SensorDataEntity entity = new SensorDataEntity();
        entity.setId(data.getId());
        entity.setTemperature(data.getTemperature());
        entity.setHumidity(data.getHumidity());
        entity.setLuminosity(data.getLuminosity());
        entity.setDistance(data.getDistance());
        entity.setDeviceId(data.getDeviceId());
        entity.setTimestamp(data.getTimestamp());
        return entity;
    }

    public SensorData toDomain() {
        return new SensorData(id, temperature, humidity, luminosity, distance, deviceId, timestamp);
    }
}
