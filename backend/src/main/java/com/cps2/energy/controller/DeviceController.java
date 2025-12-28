package com.cps2.energy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cps2.energy.entity.Device;
import com.cps2.energy.repository.DeviceRepository;
import com.cps2.energy.repository.UserRepository;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public DeviceController(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Device> getAllDevices(@RequestParam(required = false) Long userId,
                                      @RequestParam(required = false) String status) {
        if (userId != null && status != null) {
            return deviceRepository.findByUserIdAndStatus(userId, status);
        } else if (userId != null) {
            return deviceRepository.findByUserId(userId);
        } else if (status != null) {
            return deviceRepository.findByStatus(status);
        }
        return deviceRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            return ResponseEntity.ok(device.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        if (device.getUser() == null || device.getUser().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!userRepository.existsById(device.getUser().getId())) {
            return ResponseEntity.badRequest().build();
        }

        Device newDevice = deviceRepository.save(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDevice);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails) {
        Optional<Device> existingDevice = deviceRepository.findById(id);

        if (!existingDevice.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Device device = existingDevice.get();
        device.setName(deviceDetails.getName());
        device.setType(deviceDetails.getType());
        device.setStatus(deviceDetails.getStatus());
        device.setLocation(deviceDetails.getLocation());

        Device updatedDevice = deviceRepository.save(device);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        Optional<Device> existingDevice = deviceRepository.findById(id);

        if (!existingDevice.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Device device = existingDevice.get();
        device.setActive(false);
        deviceRepository.save(device);

        return ResponseEntity.noContent().build();
    }
}
