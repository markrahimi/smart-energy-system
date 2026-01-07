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

import com.cps2.energy.application.EnergyReadingService;
import com.cps2.energy.domain.EnergyReading;

@RestController
@RequestMapping("/api/energy-readings")
class EnergyReadingController {

    private final EnergyReadingService energyReadingService;

    EnergyReadingController(EnergyReadingService energyReadingService) {
        this.energyReadingService = energyReadingService;
    }

    @GetMapping
    List<EnergyReadingRepresentation> getAllReadings(
            @RequestParam(required = false) UUID deviceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return energyReadingService.getAllReadings(deviceId, start, end).stream()
                .map(EnergyReadingRepresentation::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    ResponseEntity<EnergyReadingRepresentation> getReadingById(@PathVariable UUID id) {
        try {
            EnergyReading reading = energyReadingService.getReadingById(id);
            return ResponseEntity.ok(EnergyReadingRepresentation.fromDomain(reading));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latest")
    ResponseEntity<EnergyReadingRepresentation> getLatestReading(@RequestParam UUID deviceId) {
        try {
            EnergyReading reading = energyReadingService.getLatestReading(deviceId);
            return ResponseEntity.ok(EnergyReadingRepresentation.fromDomain(reading));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<EnergyReadingRepresentation> createReading(@RequestBody EnergyReadingToCreateRepresentation readingToCreate) {
        try {
            EnergyReading reading = EnergyReading.newReading(
                    readingToCreate.voltage(),
                    readingToCreate.current(),
                    readingToCreate.power(),
                    readingToCreate.energyConsumed(),
                    readingToCreate.deviceId()
            );
            EnergyReading createdReading = energyReadingService.createReading(reading);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdReading.getId())
                    .toUri();

            return ResponseEntity.created(location).body(EnergyReadingRepresentation.fromDomain(createdReading));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
