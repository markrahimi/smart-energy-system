package com.cps2.energy.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.EnergyReading;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "energy_readings")
public class EnergyReadingEntity {

    @Id
    private UUID id;

    @Column(name = "voltage", nullable = false)
    private Double voltage;

    @Column(name = "current", nullable = false)
    private Double current;

    @Column(name = "power", nullable = false)
    private Double power;

    @Column(name = "energy_consumed")
    private Double energyConsumed;

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

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(Double energyConsumed) {
        this.energyConsumed = energyConsumed;
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

    public static EnergyReadingEntity fromDomain(EnergyReading reading) {
        EnergyReadingEntity entity = new EnergyReadingEntity();
        entity.setId(reading.getId());
        entity.setVoltage(reading.getVoltage());
        entity.setCurrent(reading.getCurrent());
        entity.setPower(reading.getPower());
        entity.setEnergyConsumed(reading.getEnergyConsumed());
        entity.setDeviceId(reading.getDeviceId());
        entity.setTimestamp(reading.getTimestamp());
        return entity;
    }

    public EnergyReading toDomain() {
        return new EnergyReading(id, voltage, current, power, energyConsumed, deviceId, timestamp);
    }
}
