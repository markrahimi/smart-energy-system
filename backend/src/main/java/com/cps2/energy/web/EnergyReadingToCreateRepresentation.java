package com.cps2.energy.web;

import java.util.UUID;

public record EnergyReadingToCreateRepresentation(Double voltage, Double current, Double power, Double energyConsumed, UUID deviceId) {
}
