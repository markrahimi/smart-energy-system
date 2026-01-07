package com.cps2.energy.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.Device;
import com.cps2.energy.persistence.DeviceEntity;
import com.cps2.energy.persistence.DeviceRepository;
import com.cps2.energy.persistence.UserRepository;


@Service
@Transactional
public class DeviceService {

    private final DeviceRepository repository;
    private final UserRepository userRepository;

    DeviceService(DeviceRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Device> getAllDevices(UUID userId, String status) {
        List<DeviceEntity> entities;
        if (userId != null && status != null) {
            entities = repository.findByUserIdAndStatus(userId, status);
        } else if (userId != null) {
            entities = repository.findByUserId(userId);
        } else if (status != null) {
            entities = repository.findByStatus(status);
        } else {
            entities = repository.findAll();
        }
        return entities.stream().map(DeviceEntity::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Device getDeviceById(UUID id) {
        return repository.findById(id)
                .map(DeviceEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Device not found with id: " + id));
    }

    public Device createDevice(Device device) {
        userRepository.findById(device.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + device.getUserId()));

        DeviceEntity entity = DeviceEntity.fromDomain(device);
        DeviceEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    public Device updateDevice(UUID id, Device deviceDetails) {
        DeviceEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device not found with id: " + id));

        existingEntity.setName(deviceDetails.getName());
        existingEntity.setType(deviceDetails.getType());
        existingEntity.setStatus(deviceDetails.getStatus());
        existingEntity.setLocation(deviceDetails.getLocation());

        DeviceEntity updatedEntity = repository.save(existingEntity);
        return updatedEntity.toDomain();
    }

    public void deleteDevice(UUID id) {
        DeviceEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device not found with id: " + id));
        entity.setActive(false);
        repository.save(entity);
    }
}
