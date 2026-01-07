package com.cps2.energy.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.SensorData;
import com.cps2.energy.persistence.DeviceRepository;
import com.cps2.energy.persistence.SensorDataEntity;
import com.cps2.energy.persistence.SensorDataRepository;



@Service
@Transactional
public class SensorDataService {

    private final SensorDataRepository repository;
    private final DeviceRepository deviceRepository;

    SensorDataService(SensorDataRepository repository, DeviceRepository deviceRepository) {
        this.repository = repository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional(readOnly = true)
    public List<SensorData> getAllSensorData(UUID deviceId, LocalDateTime start, LocalDateTime end) {
        List<SensorDataEntity> entities;
        if (deviceId != null && start != null && end != null) {
            entities = repository.findByDeviceIdAndTimestampBetween(deviceId, start, end);
        } else if (deviceId != null) {
            entities = repository.findByDeviceIdOrderByTimestampDesc(deviceId);
        } else if (start != null && end != null) {
            entities = repository.findByTimestampBetween(start, end);
        } else {
            entities = repository.findAll();
        }
        return entities.stream().map(SensorDataEntity::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public SensorData getSensorDataById(UUID id) {
        return repository.findById(id)
                .map(SensorDataEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Sensor data not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public SensorData getLatestSensorData(UUID deviceId) {
        List<SensorDataEntity> data = repository.findByDeviceIdOrderByTimestampDesc(deviceId);
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No sensor data found for device: " + deviceId);
        }
        return data.get(0).toDomain();
    }

    public SensorData createSensorData(SensorData data) {
        deviceRepository.findById(data.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("Device not found with id: " + data.getDeviceId()));

        SensorDataEntity entity = SensorDataEntity.fromDomain(data);
        SensorDataEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }
}
