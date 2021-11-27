package com.challenge.truphonechallenge.dataprovider;

import com.challenge.truphonechallenge.model.Device;

import java.util.List;

public interface IDeviceDataProvider {

    List<Device> listDevices();

    Device getDevice(Long id);

    Device saveDevice(Device device);

    void deleteDevice(Long id);

    void drop();
}
