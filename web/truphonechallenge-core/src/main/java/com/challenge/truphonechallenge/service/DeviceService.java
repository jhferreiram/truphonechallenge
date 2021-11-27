package com.challenge.truphonechallenge.service;

import com.challenge.truphonechallenge.dto.CreateDeviceDTO;
import com.challenge.truphonechallenge.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {

    List<DeviceDTO> listDevices();

    DeviceDTO getDevice(Long id);

    DeviceDTO createDevice(CreateDeviceDTO request);

    DeviceDTO updateDevice(Long id, CreateDeviceDTO request);

    DeviceDTO patchDevice(Long id, CreateDeviceDTO request);

    DeviceDTO deleteDevice(Long id);
}
