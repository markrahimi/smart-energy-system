package com.cps2.energy.web;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cps2.energy.domain.Device;

public record DeviceRepresentation(UUID id, String name, String type, String status, String location, Boolean active, UUID userId, LocalDateTime createdAt) {

    public static DeviceRepresentation fromDomain(Device device) {
        return new DeviceRepresentation(device.getId(), device.getName(), device.getType(), device.getStatus(), device.getLocation(), device.getActive(), device.getUserId(), device.getCreatedAt());
    }
}
