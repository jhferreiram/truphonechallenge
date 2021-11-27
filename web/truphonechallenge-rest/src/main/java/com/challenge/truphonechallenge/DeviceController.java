package com.challenge.truphonechallenge;

import com.challenge.truphonechallenge.dto.CreateDeviceDTO;
import com.challenge.truphonechallenge.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController implements DeviceAPI {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public ResponseEntity listDevices() {
        return ResponseEntity.ok(this.deviceService.listDevices());
    }

    @Override
    public ResponseEntity getDevice(Long id) {
        return ResponseEntity.ok(this.deviceService.getDevice(id));
    }

    @Override
    public ResponseEntity createDevice(CreateDeviceDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.deviceService.createDevice(input));
    }

    @Override
    public ResponseEntity updateDevice(Long id, CreateDeviceDTO input) {
        return ResponseEntity.ok(this.deviceService.updateDevice(id, input));
    }

    @Override
    public ResponseEntity patchDevice(Long id, CreateDeviceDTO input) {
        return ResponseEntity.ok(this.deviceService.patchDevice(id, input));
    }

    @Override
    public ResponseEntity deleteDevice(Long id) {
        return ResponseEntity.ok(this.deviceService.deleteDevice(id));
    }
}
