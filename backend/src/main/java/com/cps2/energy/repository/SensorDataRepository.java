package com.cps2.energy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cps2.energy.entity.SensorData;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findByDeviceId(Long deviceId);

    List<SensorData> findByDeviceIdOrderByTimestampDesc(Long deviceId);

    List<SensorData> findByDeviceIdAndTimestampBetween(Long deviceId, LocalDateTime start, LocalDateTime end);

    List<SensorData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
