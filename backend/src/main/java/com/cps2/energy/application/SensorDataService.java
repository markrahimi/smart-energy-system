package com.cps2.energy.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps2.energy.domain.Notification;
import com.cps2.energy.domain.SensorData;
import com.cps2.energy.domain.Threshold;
import com.cps2.energy.persistence.DeviceRepository;
import com.cps2.energy.persistence.SensorDataEntity;
import com.cps2.energy.persistence.SensorDataRepository;

@Service
@Transactional
public class SensorDataService {

    private final SensorDataRepository repository;
    private final DeviceRepository deviceRepository;
    private final ThresholdService thresholdService;
    private final NotificationService notificationService;

    SensorDataService(SensorDataRepository repository, DeviceRepository deviceRepository,
            ThresholdService thresholdService, NotificationService notificationService) {
        this.repository = repository;
        this.deviceRepository = deviceRepository;
        this.thresholdService = thresholdService;
        this.notificationService = notificationService;
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

        checkThresholds(data);

        return savedEntity.toDomain();
    }

    private void checkThresholds(SensorData data) {
        try {
            Threshold threshold = thresholdService.getThresholdByDeviceId(data.getDeviceId());

            if (!threshold.isActive()) {
                return;
            }

            StringBuilder violations = new StringBuilder();

            if (threshold.getMinTemperature() != null && data.getTemperature() != null
                    && data.getTemperature() < threshold.getMinTemperature()) {
                violations.append("Temperature below minimum (").append(data.getTemperature())
                        .append(" < ").append(threshold.getMinTemperature()).append("). ");
            }
            if (threshold.getMaxTemperature() != null && data.getTemperature() != null
                    && data.getTemperature() > threshold.getMaxTemperature()) {
                violations.append("Temperature above maximum (").append(data.getTemperature())
                        .append(" > ").append(threshold.getMaxTemperature()).append("). ");
            }

            if (threshold.getMinHumidity() != null && data.getHumidity() != null
                    && data.getHumidity() < threshold.getMinHumidity()) {
                violations.append("Humidity below minimum (").append(data.getHumidity())
                        .append(" < ").append(threshold.getMinHumidity()).append("). ");
            }
            if (threshold.getMaxHumidity() != null && data.getHumidity() != null
                    && data.getHumidity() > threshold.getMaxHumidity()) {
                violations.append("Humidity above maximum (").append(data.getHumidity())
                        .append(" > ").append(threshold.getMaxHumidity()).append("). ");
            }

            if (threshold.getMinLuminosity() != null && data.getLuminosity() != null
                    && data.getLuminosity() < threshold.getMinLuminosity()) {
                violations.append("Luminosity below minimum (").append(data.getLuminosity())
                        .append(" < ").append(threshold.getMinLuminosity()).append("). ");
            }
            if (threshold.getMaxLuminosity() != null && data.getLuminosity() != null
                    && data.getLuminosity() > threshold.getMaxLuminosity()) {
                violations.append("Luminosity above maximum (").append(data.getLuminosity())
                        .append(" > ").append(threshold.getMaxLuminosity()).append("). ");
            }

            if (threshold.getMinPower() != null && data.getPowerConsumption() != null
                    && data.getPowerConsumption() < threshold.getMinPower()) {
                violations.append("Power below minimum (").append(data.getPowerConsumption())
                        .append(" < ").append(threshold.getMinPower()).append("). ");
            }
            if (threshold.getMaxPower() != null && data.getPowerConsumption() != null
                    && data.getPowerConsumption() > threshold.getMaxPower()) {
                violations.append("Power above maximum (").append(data.getPowerConsumption())
                        .append(" > ").append(threshold.getMaxPower()).append("). ");
            }

            if (threshold.getMinVoltage() != null && data.getVoltage() != null
                    && data.getVoltage() < threshold.getMinVoltage()) {
                violations.append("Voltage below minimum (").append(data.getVoltage())
                        .append(" < ").append(threshold.getMinVoltage()).append("). ");
            }
            if (threshold.getMaxVoltage() != null && data.getVoltage() != null
                    && data.getVoltage() > threshold.getMaxVoltage()) {
                violations.append("Voltage above maximum (").append(data.getVoltage())
                        .append(" > ").append(threshold.getMaxVoltage()).append("). ");
            }

            if (threshold.getMinCurrent() != null && data.getCurrent() != null
                    && data.getCurrent() < threshold.getMinCurrent()) {
                violations.append("Current below minimum (").append(data.getCurrent())
                        .append(" < ").append(threshold.getMinCurrent()).append("). ");
            }
            if (threshold.getMaxCurrent() != null && data.getCurrent() != null
                    && data.getCurrent() > threshold.getMaxCurrent()) {
                violations.append("Current above maximum (").append(data.getCurrent())
                        .append(" > ").append(threshold.getMaxCurrent()).append("). ");
            }

            if (violations.length() > 0) {
                Notification notification = Notification.newThresholdNotification(
                        "Threshold Violation Alert",
                        violations.toString().trim(),
                        "THRESHOLD_VIOLATION",
                        "HIGH",
                        threshold.getUserId(),
                        threshold.getId());
                notificationService.createNotification(notification);
            }
        } catch (IllegalArgumentException e) {
            // No threshold configured for this device, skip checking
        }
    }
}
