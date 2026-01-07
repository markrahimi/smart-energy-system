package com.cps2.energy.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdRepository extends JpaRepository<ThresholdEntity, UUID> {
    Optional<ThresholdEntity> findByDeviceId(UUID deviceId);

    List<ThresholdEntity> findByUserId(UUID userId);

    List<ThresholdEntity> findByUserIdAndActiveTrue(UUID userId);
}
