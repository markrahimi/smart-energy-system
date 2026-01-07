package com.cps2.energy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class SensorData {

    private final UUID id;
    private final Double temperature;
    private final Double humidity;
    private final Double luminosity;
    private final Double powerConsumption;
    private final Double voltage;
    private final Double current;
    private final UUID deviceId;
    private final LocalDateTime timestamp;

    public SensorData(UUID id, Double temperature, Double humidity, Double luminosity,
            Double powerConsumption, Double voltage, Double current,
            UUID deviceId, LocalDateTime timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.luminosity = luminosity;
        this.powerConsumption = powerConsumption;
        this.voltage = voltage;
        this.current = current;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public static SensorData newSensorData(Double temperature, Double humidity, Double luminosity,
            Double powerConsumption, Double voltage, Double current,
            UUID deviceId) {
        return new SensorData(UUID.randomUUID(), temperature, humidity, luminosity,
                powerConsumption, voltage, current, deviceId, LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getLuminosity() {
        return luminosity;
    }

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    public Double getVoltage() {
        return voltage;
    }

    public Double getCurrent() {
        return current;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
