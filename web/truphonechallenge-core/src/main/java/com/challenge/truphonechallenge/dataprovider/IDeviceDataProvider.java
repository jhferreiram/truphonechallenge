package com.challenge.truphonechallenge.dataprovider;

import com.challenge.truphonechallenge.model.Device;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IDeviceDataProvider {

    List<Device> listDevices(PageRequest pageRequest, String brand);

    Device getDevice(Long id);

    Device saveDevice(Device device);

    void deleteDevice(Long id);

    void drop();
}
