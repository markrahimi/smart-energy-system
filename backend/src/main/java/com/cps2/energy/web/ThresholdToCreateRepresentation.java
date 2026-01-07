package com.cps2.energy.web;

import java.util.UUID;

public record ThresholdToCreateRepresentation(UUID deviceId, UUID userId,
                Double minTemperature, Double maxTemperature,
                Double minHumidity, Double maxHumidity,
                Double minLuminosity, Double maxLuminosity,
                Double minPower, Double maxPower,
                Double minVoltage, Double maxVoltage,
                Double minCurrent, Double maxCurrent) {
}
