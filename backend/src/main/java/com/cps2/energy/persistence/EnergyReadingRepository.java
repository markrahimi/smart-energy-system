package com.cps2.energy.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyReadingRepository extends JpaRepository<EnergyReadingEntity, UUID> {
    List<EnergyReadingEntity> findByDeviceId(UUID deviceId);

    List<EnergyReadingEntity> findByDeviceIdOrderByTimestampDesc(UUID deviceId);

    List<EnergyReadingEntity> findByDeviceIdAndTimestampBetween(UUID deviceId, LocalDateTime start, LocalDateTime end);

    List<EnergyReadingEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
