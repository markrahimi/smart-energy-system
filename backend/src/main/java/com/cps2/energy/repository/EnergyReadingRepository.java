package com.cps2.energy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cps2.energy.entity.EnergyReading;

@Repository
public interface EnergyReadingRepository extends JpaRepository<EnergyReading, Long> {
    List<EnergyReading> findByDeviceId(Long deviceId);

    List<EnergyReading> findByDeviceIdOrderByTimestampDesc(Long deviceId);

    List<EnergyReading> findByDeviceIdAndTimestampBetween(Long deviceId, LocalDateTime start, LocalDateTime end);

    List<EnergyReading> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
