package com.cps2.energy.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.EnergyReading;
import com.cps2.energy.persistence.DeviceRepository;
import com.cps2.energy.persistence.EnergyReadingEntity;
import com.cps2.energy.persistence.EnergyReadingRepository;

@Service
@Transactional
public class EnergyReadingService {

    private final EnergyReadingRepository repository;
    private final DeviceRepository deviceRepository;

    EnergyReadingService(EnergyReadingRepository repository, DeviceRepository deviceRepository) {
        this.repository = repository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional(readOnly = true)
    public List<EnergyReading> getAllReadings(UUID deviceId, LocalDateTime start, LocalDateTime end) {
        List<EnergyReadingEntity> entities;
        if (deviceId != null && start != null && end != null) {
            entities = repository.findByDeviceIdAndTimestampBetween(deviceId, start, end);
        } else if (deviceId != null) {
            entities = repository.findByDeviceIdOrderByTimestampDesc(deviceId);
        } else if (start != null && end != null) {
            entities = repository.findByTimestampBetween(start, end);
        } else {
            entities = repository.findAll();
        }
        return entities.stream().map(EnergyReadingEntity::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public EnergyReading getReadingById(UUID id) {
        return repository.findById(id)
                .map(EnergyReadingEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Energy reading not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public EnergyReading getLatestReading(UUID deviceId) {
        List<EnergyReadingEntity> readings = repository.findByDeviceIdOrderByTimestampDesc(deviceId);
        if (readings.isEmpty()) {
            throw new IllegalArgumentException("No readings found for device: " + deviceId);
        }
        return readings.get(0).toDomain();
    }

    public EnergyReading createReading(EnergyReading reading) {
        deviceRepository.findById(reading.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("Device not found with id: " + reading.getDeviceId()));

        EnergyReadingEntity entity = EnergyReadingEntity.fromDomain(reading);
        EnergyReadingEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }
}
