package com.cps2.energy.persistence;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    List<DeviceEntity> findByUserId(UUID userId);

    List<DeviceEntity> findByUserIdAndActive(UUID userId, Boolean active);

    List<DeviceEntity> findByStatus(String status);

    List<DeviceEntity> findByType(String type);

    List<DeviceEntity> findByUserIdAndStatus(UUID userId, String status);
}
