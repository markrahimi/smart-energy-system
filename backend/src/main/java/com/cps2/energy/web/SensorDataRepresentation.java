package com.cps2.energy.web;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.SensorData;

public record SensorDataRepresentation(UUID id, Double temperature, Double humidity, Double luminosity,
        Double powerConsumption, Double voltage, Double current, UUID deviceId, LocalDateTime timestamp) {

    public static SensorDataRepresentation fromDomain(SensorData data) {
        return new SensorDataRepresentation(data.getId(), data.getTemperature(), data.getHumidity(),
                data.getLuminosity(), data.getPowerConsumption(), data.getVoltage(), data.getCurrent(),
                data.getDeviceId(), data.getTimestamp());
    }
}
