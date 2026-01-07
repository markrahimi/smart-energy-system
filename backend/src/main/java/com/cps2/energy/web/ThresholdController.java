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

import com.cps2.energy.application.ThresholdService;
import com.cps2.energy.domain.Threshold;

@RestController
@RequestMapping("/api/thresholds")
class ThresholdController {

    private final ThresholdService thresholdService;

    ThresholdController(ThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

    @GetMapping
    List<ThresholdRepresentation> getThresholds(@RequestParam(required = false) UUID userId) {
        if (userId != null) {
            return thresholdService.getThresholdsByUserId(userId).stream()
                    .map(ThresholdRepresentation::fromDomain)
                    .toList();
        } else {
            return thresholdService.getAllThresholds().stream()
                    .map(ThresholdRepresentation::fromDomain)
                    .toList();
        }
    }

    @GetMapping("/device/{deviceId}")
    ResponseEntity<ThresholdRepresentation> getThresholdByDeviceId(@PathVariable UUID deviceId) {
        try {
            Threshold threshold = thresholdService.getThresholdByDeviceId(deviceId);
            return ResponseEntity.ok(ThresholdRepresentation.fromDomain(threshold));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<ThresholdRepresentation> createThreshold(
            @RequestBody ThresholdToCreateRepresentation thresholdToCreate) {
        try {
            Threshold threshold = Threshold.newThreshold(
                    thresholdToCreate.deviceId(),
                    thresholdToCreate.userId(),
                    thresholdToCreate.minTemperature(),
                    thresholdToCreate.maxTemperature(),
                    thresholdToCreate.minHumidity(),
                    thresholdToCreate.maxHumidity(),
                    thresholdToCreate.minLuminosity(),
                    thresholdToCreate.maxLuminosity(),
                    thresholdToCreate.minPower(),
                    thresholdToCreate.maxPower(),
                    thresholdToCreate.minVoltage(),
                    thresholdToCreate.maxVoltage(),
                    thresholdToCreate.minCurrent(),
                    thresholdToCreate.maxCurrent());
            Threshold createdThreshold = thresholdService.createThreshold(threshold);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/device/{deviceId}")
                    .buildAndExpand(createdThreshold.getDeviceId())
                    .toUri();

            return ResponseEntity.created(location).body(ThresholdRepresentation.fromDomain(createdThreshold));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/device/{deviceId}")
    ResponseEntity<ThresholdRepresentation> updateThreshold(@PathVariable UUID deviceId,
            @RequestBody ThresholdToCreateRepresentation thresholdToUpdate) {
        try {
            Threshold threshold = Threshold.newThreshold(
                    deviceId,
                    thresholdToUpdate.userId(),
                    thresholdToUpdate.minTemperature(),
                    thresholdToUpdate.maxTemperature(),
                    thresholdToUpdate.minHumidity(),
                    thresholdToUpdate.maxHumidity(),
                    thresholdToUpdate.minLuminosity(),
                    thresholdToUpdate.maxLuminosity(),
                    thresholdToUpdate.minPower(),
                    thresholdToUpdate.maxPower(),
                    thresholdToUpdate.minVoltage(),
                    thresholdToUpdate.maxVoltage(),
                    thresholdToUpdate.minCurrent(),
                    thresholdToUpdate.maxCurrent());
            Threshold updatedThreshold = thresholdService.updateThreshold(deviceId, threshold);
            return ResponseEntity.ok(ThresholdRepresentation.fromDomain(updatedThreshold));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/device/{deviceId}")
    ResponseEntity<Void> deleteThreshold(@PathVariable UUID deviceId) {
        try {
            thresholdService.deleteThreshold(deviceId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
