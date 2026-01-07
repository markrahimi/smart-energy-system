package com.cps2.energy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class EnergyReading {

    private final UUID id;
    private final Double voltage;
    private final Double current;
    private final Double power;
    private final Double energyConsumed;
    private final UUID deviceId;
    private final LocalDateTime timestamp;

    public EnergyReading(UUID id, Double voltage, Double current, Double power, Double energyConsumed, UUID deviceId, LocalDateTime timestamp) {
        this.id = id;
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.energyConsumed = energyConsumed;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public static EnergyReading newReading(Double voltage, Double current, Double power, Double energyConsumed, UUID deviceId) {
        return new EnergyReading(UUID.randomUUID(), voltage, current, power, energyConsumed, deviceId, LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public Double getVoltage() {
        return voltage;
    }

    public Double getCurrent() {
        return current;
    }

    public Double getPower() {
        return power;
    }

    public Double getEnergyConsumed() {
        return energyConsumed;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
