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

    @Column(name = "power_consumption")
    private Double powerConsumption;

    @Column(name = "voltage")
    private Double voltage;

    @Column(name = "current")
    private Double current;

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

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
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
        entity.setPowerConsumption(data.getPowerConsumption());
        entity.setVoltage(data.getVoltage());
        entity.setCurrent(data.getCurrent());
        entity.setDeviceId(data.getDeviceId());
        entity.setTimestamp(data.getTimestamp());
        return entity;
    }

    public SensorData toDomain() {
        return new SensorData(id, temperature, humidity, luminosity,
                powerConsumption, voltage, current, deviceId, timestamp);
    }
}
