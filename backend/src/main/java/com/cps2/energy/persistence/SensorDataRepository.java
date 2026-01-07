package com.cps2.energy.persistence;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorDataEntity, UUID> {
    List<SensorDataEntity> findByDeviceId(UUID deviceId);

    List<SensorDataEntity> findByDeviceIdOrderByTimestampDesc(UUID deviceId);

    List<SensorDataEntity> findByDeviceIdAndTimestampBetween(UUID deviceId, LocalDateTime start, LocalDateTime end);

    List<SensorDataEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
