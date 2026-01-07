package com.cps2.energy.web;

import java.util.UUID;

public record DeviceToCreateRepresentation(String name, String type, String location, UUID userId) {
}
