package com.cps2.energy.web;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.Threshold;

public record ThresholdRepresentation(UUID id, UUID deviceId, UUID userId,
        Double minTemperature, Double maxTemperature,
        Double minHumidity, Double maxHumidity,
        Double minLuminosity, Double maxLuminosity,
        Double minPower, Double maxPower,
        Double minVoltage, Double maxVoltage,
        Double minCurrent, Double maxCurrent,
        boolean active, LocalDateTime createdAt) {

    public static ThresholdRepresentation fromDomain(Threshold threshold) {
        return new ThresholdRepresentation(
                threshold.getId(),
                threshold.getDeviceId(),
                threshold.getUserId(),
                threshold.getMinTemperature(),
                threshold.getMaxTemperature(),
                threshold.getMinHumidity(),
                threshold.getMaxHumidity(),
                threshold.getMinLuminosity(),
                threshold.getMaxLuminosity(),
                threshold.getMinPower(),
                threshold.getMaxPower(),
                threshold.getMinVoltage(),
                threshold.getMaxVoltage(),
                threshold.getMinCurrent(),
                threshold.getMaxCurrent(),
                threshold.isActive(),
                threshold.getCreatedAt());
    }
}
