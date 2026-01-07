package com.cps2.energy.web;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cps2.energy.application.SensorDataService;
import com.cps2.energy.domain.SensorData;

@RestController
@RequestMapping("/api/sensor-data")
class SensorDataController {

    private final SensorDataService sensorDataService;

    SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping
    List<SensorDataRepresentation> getAllSensorData(
            @RequestParam(required = false) UUID deviceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return sensorDataService.getAllSensorData(deviceId, start, end).stream()
                .map(SensorDataRepresentation::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<SensorDataRepresentation> getSensorDataById(@PathVariable UUID id) {
        try {
            SensorData data = sensorDataService.getSensorDataById(id);
            return ResponseEntity.ok(SensorDataRepresentation.fromDomain(data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latest")
    ResponseEntity<SensorDataRepresentation> getLatestSensorData(@RequestParam UUID deviceId) {
        try {
            SensorData data = sensorDataService.getLatestSensorData(deviceId);
            return ResponseEntity.ok(SensorDataRepresentation.fromDomain(data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<SensorDataRepresentation> createSensorData(
            @RequestBody SensorDataToCreateRepresentation dataToCreate) {
        try {
            SensorData data = SensorData.newSensorData(
                    dataToCreate.temperature(),
                    dataToCreate.humidity(),
                    dataToCreate.luminosity(),
                    dataToCreate.powerConsumption(),
                    dataToCreate.voltage(),
                    dataToCreate.current(),
                    dataToCreate.deviceId());
            SensorData createdData = sensorDataService.createSensorData(data);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdData.getId())
                    .toUri();

            return ResponseEntity.created(location).body(SensorDataRepresentation.fromDomain(createdData));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
