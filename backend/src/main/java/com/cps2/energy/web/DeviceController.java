package com.cps2.energy.web;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cps2.energy.application.DeviceService;
import com.cps2.energy.domain.Device;

@RestController
@RequestMapping("/api/devices")
class DeviceController {

    private final DeviceService deviceService;

    DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    List<DeviceRepresentation> getAllDevices(@RequestParam(required = false) UUID userId,
                                             @RequestParam(required = false) String status) {
        return deviceService.getAllDevices(userId, status).stream()
                .map(DeviceRepresentation::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<DeviceRepresentation> getDeviceById(@PathVariable UUID id) {
        try {
            Device device = deviceService.getDeviceById(id);
            return ResponseEntity.ok(DeviceRepresentation.fromDomain(device));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<DeviceRepresentation> createDevice(@RequestBody DeviceToCreateRepresentation deviceToCreate) {
        try {
            Device device = Device.newDevice(
                    deviceToCreate.name(),
                    deviceToCreate.type(),
                    deviceToCreate.location(),
                    deviceToCreate.userId()
            );
            Device createdDevice = deviceService.createDevice(device);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdDevice.getId())
                    .toUri();

            return ResponseEntity.created(location).body(DeviceRepresentation.fromDomain(createdDevice));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<DeviceRepresentation> updateDevice(@PathVariable UUID id, @RequestBody DeviceToUpdateRepresentation deviceToUpdate) {
        try {
            Device existingDevice = deviceService.getDeviceById(id);
            Device updatedDevice = existingDevice.updateInfo(
                    deviceToUpdate.name(),
                    deviceToUpdate.type(),
                    deviceToUpdate.status(),
                    deviceToUpdate.location()
            );
            Device savedDevice = deviceService.updateDevice(id, updatedDevice);
            return ResponseEntity.ok(DeviceRepresentation.fromDomain(savedDevice));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        try {
            deviceService.deleteDevice(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
