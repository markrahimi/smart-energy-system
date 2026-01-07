package com.cps2.energy.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.Threshold;
import com.cps2.energy.persistence.ThresholdEntity;
import com.cps2.energy.persistence.ThresholdRepository;

@Service
@Transactional
public class ThresholdService {

    private final ThresholdRepository repository;

    ThresholdService(ThresholdRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Threshold> getThresholdsByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(ThresholdEntity::toDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public Threshold getThresholdByDeviceId(UUID deviceId) {
        return repository.findByDeviceId(deviceId)
                .map(ThresholdEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Threshold not found for device: " + deviceId));
    }

    public Threshold createThreshold(Threshold threshold) {
        ThresholdEntity entity = ThresholdEntity.fromDomain(threshold);
        ThresholdEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    public Threshold updateThreshold(UUID deviceId, Threshold threshold) {
        repository.findByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Threshold not found for device: " + deviceId));

        ThresholdEntity entity = ThresholdEntity.fromDomain(threshold);
        ThresholdEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    public void deleteThreshold(UUID deviceId) {
        ThresholdEntity entity = repository.findByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Threshold not found for device: " + deviceId));
        repository.delete(entity);
    }
}
