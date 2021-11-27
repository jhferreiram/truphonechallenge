package com.challenge.truphonechallenge.impl;

import com.challenge.truphonechallenge.model.Device;
import com.challenge.truphonechallenge.dataprovider.IDeviceDataProvider;
import com.challenge.truphonechallenge.entity.DeviceEntity;
import com.challenge.truphonechallenge.repository.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeviceDataProviderImpl implements IDeviceDataProvider {

    private final IDeviceRepository repo;

    @Autowired
    public DeviceDataProviderImpl(IDeviceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Device> listDevices() {
        return this.repo.findAll().stream().map(this::toDevice).collect(Collectors.toList());
    }

    @Override
    public Device getDevice(Long id) {
        return this.toDevice(this.repo.getById(id));
    }

    @Override
    public Device saveDevice(Device device) {
        return this.toDevice(this.repo.saveAndFlush(this.toDeviceEntity(device)));
    }

    @Override
    public void deleteDevice(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public void drop() {
        this.repo.deleteAll();
    }

    private Device toDevice(DeviceEntity entity) {
        if (entity == null) return null;
        return Device.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .time(entity.getTime())
                .build();
    }

    private DeviceEntity toDeviceEntity(Device device) {
        if (device == null) return null;
        return DeviceEntity.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .time(device.getTime())
                .build();
    }
}
