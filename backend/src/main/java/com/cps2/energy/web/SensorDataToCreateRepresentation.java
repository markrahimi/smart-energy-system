package com.cps2.energy.web;

import java.util.UUID;

public record SensorDataToCreateRepresentation(Double temperature, Double humidity, Double luminosity, Double distance, UUID deviceId) {
}
