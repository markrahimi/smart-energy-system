package com.cps2.energy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cps2.energy.entity.EnergyReading;
import com.cps2.energy.repository.DeviceRepository;
import com.cps2.energy.repository.EnergyReadingRepository;

@RestController
@RequestMapping("/api/energy-readings")
public class EnergyReadingController {

    private final EnergyReadingRepository energyReadingRepository;
    private final DeviceRepository deviceRepository;

    public EnergyReadingController(EnergyReadingRepository energyReadingRepository,
                                   DeviceRepository deviceRepository) {
        this.energyReadingRepository = energyReadingRepository;
        this.deviceRepository = deviceRepository;
    }

    @GetMapping
    public List<EnergyReading> getAllReadings(
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        if (deviceId != null && start != null && end != null) {
            return energyReadingRepository.findByDeviceIdAndTimestampBetween(deviceId, start, end);
        } else if (deviceId != null) {
            return energyReadingRepository.findByDeviceIdOrderByTimestampDesc(deviceId);
        } else if (start != null && end != null) {
            return energyReadingRepository.findByTimestampBetween(start, end);
        }
        return energyReadingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergyReading> getReadingById(@PathVariable Long id) {
        Optional<EnergyReading> reading = energyReadingRepository.findById(id);
        if (reading.isPresent()) {
            return ResponseEntity.ok(reading.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/device/{deviceId}/latest")
    public ResponseEntity<EnergyReading> getLatestReading(@PathVariable Long deviceId) {
        List<EnergyReading> readings = energyReadingRepository.findByDeviceIdOrderByTimestampDesc(deviceId);
        if (!readings.isEmpty()) {
            return ResponseEntity.ok(readings.get(0));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EnergyReading> createReading(@RequestBody EnergyReading reading) {
        if (reading.getDevice() == null || reading.getDevice().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!deviceRepository.existsById(reading.getDevice().getId())) {
            return ResponseEntity.badRequest().build();
        }

        EnergyReading newReading = energyReadingRepository.save(reading);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReading);
    }
}
