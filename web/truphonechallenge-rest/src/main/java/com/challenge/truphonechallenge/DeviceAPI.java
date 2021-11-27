package com.challenge.truphonechallenge;

import com.challenge.truphonechallenge.dto.CreateDeviceDTO;
import com.challenge.truphonechallenge.dto.DeviceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DeviceAPI {

    @GetMapping("/devices")
    ResponseEntity<List<DeviceDTO>> listDevices(@RequestParam(required = false, defaultValue = "0") int page,
                                                @RequestParam(required = false, defaultValue = "5") int size,
                                                @RequestParam(required = false) String brand);

    @GetMapping("/devices/{id}")
    ResponseEntity<DeviceDTO> getDevice(@PathVariable Long id);

    @PostMapping("/devices")
    ResponseEntity<DeviceDTO> createDevice(@RequestBody CreateDeviceDTO input);

    @PutMapping("/devices/{id}")
    ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long id, @RequestBody CreateDeviceDTO input);

    @PatchMapping("/devices/{id}")
    ResponseEntity<DeviceDTO> patchDevice(@PathVariable Long id, @RequestBody CreateDeviceDTO input);

    @DeleteMapping("/devices/{id}")
    ResponseEntity<DeviceDTO> deleteDevice(@PathVariable Long id);
}
