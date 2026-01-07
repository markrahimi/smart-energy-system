package com.cps2.energy.web;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.EnergyReading;

public record EnergyReadingRepresentation(UUID id, Double voltage, Double current, Double power, Double energyConsumed, UUID deviceId, LocalDateTime timestamp) {

    public static EnergyReadingRepresentation fromDomain(EnergyReading reading) {
        return new EnergyReadingRepresentation(reading.getId(), reading.getVoltage(), reading.getCurrent(), reading.getPower(), reading.getEnergyConsumed(), reading.getDeviceId(), reading.getTimestamp());
    }
}
