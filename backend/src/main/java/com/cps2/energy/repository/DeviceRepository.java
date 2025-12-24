package com.cps2.energy.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cps2.energy.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(Long userId);

    List<Device> findByUserIdAndActive(Long userId, Boolean active);

    List<Device> findByStatus(String status);

    List<Device> findByType(String type);

    List<Device> findByUserIdAndStatus(Long userId, String status);
}
