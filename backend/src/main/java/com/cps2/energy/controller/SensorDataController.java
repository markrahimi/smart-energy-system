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

import com.cps2.energy.entity.SensorData;
import com.cps2.energy.repository.DeviceRepository;
import com.cps2.energy.repository.SensorDataRepository;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataController {

    private final SensorDataRepository sensorDataRepository;
    private final DeviceRepository deviceRepository;

    public SensorDataController(SensorDataRepository sensorDataRepository,
                                DeviceRepository deviceRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.deviceRepository = deviceRepository;
    }

    @GetMapping
    public List<SensorData> getAllSensorData(
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        if (deviceId != null && start != null && end != null) {
            return sensorDataRepository.findByDeviceIdAndTimestampBetween(deviceId, start, end);
        } else if (deviceId != null) {
            return sensorDataRepository.findByDeviceIdOrderByTimestampDesc(deviceId);
        } else if (start != null && end != null) {
            return sensorDataRepository.findByTimestampBetween(start, end);
        }
        return sensorDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorData> getSensorDataById(@PathVariable Long id) {
        Optional<SensorData> sensorData = sensorDataRepository.findById(id);
        if (sensorData.isPresent()) {
            return ResponseEntity.ok(sensorData.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/device/{deviceId}/latest")
    public ResponseEntity<SensorData> getLatestSensorData(@PathVariable Long deviceId) {
        List<SensorData> sensorDataList = sensorDataRepository.findByDeviceIdOrderByTimestampDesc(deviceId);
        if (!sensorDataList.isEmpty()) {
            return ResponseEntity.ok(sensorDataList.get(0));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SensorData> createSensorData(@RequestBody SensorData sensorData) {
        if (sensorData.getDevice() == null || sensorData.getDevice().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!deviceRepository.existsById(sensorData.getDevice().getId())) {
            return ResponseEntity.badRequest().build();
        }

        SensorData newSensorData = sensorDataRepository.save(sensorData);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSensorData);
    }
}
