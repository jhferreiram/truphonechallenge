package com.challenge.truphonechallenge.service;

import com.challenge.truphonechallenge.dataprovider.IDeviceDataProvider;
import com.challenge.truphonechallenge.dto.CreateDeviceDTO;
import com.challenge.truphonechallenge.dto.DeviceDTO;
import com.challenge.truphonechallenge.exceptions.GenericErrorException;
import com.challenge.truphonechallenge.exceptions.UserNotFoundException;
import com.challenge.truphonechallenge.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private IDeviceDataProvider dataProvider;

    @Autowired
    public DeviceServiceImpl(IDeviceDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public List<DeviceDTO> listDevices(int page, int size, String brand) {
        PageRequest pageReq = PageRequest.of(page, size);
        return dataProvider.listDevices(pageReq, brand).stream().map(this::toDeviceDTO).collect(Collectors.toList());
    }

    @Override
    public DeviceDTO getDevice(Long id) {
        return this.toDeviceDTO(this.getSingleDevice(id));
    }

    @Override
    public DeviceDTO createDevice(CreateDeviceDTO request) {
        try {
            Device device = Device.builder()
                    .name(request.getName())
                    .brand(request.getBrand())
                    .time(request.getTime())
                    .build();
            return this.toDeviceDTO(this.dataProvider.saveDevice(device));
        } catch (Exception ex) {
            throw new GenericErrorException(ex.getMessage());
        }
    }

    @Override
    public DeviceDTO updateDevice(Long id, CreateDeviceDTO request) {
        try {
            Device oldDevice = this.getSingleDevice(id);
            Device newDevice = this.toDevice(request);
            newDevice.setId(oldDevice.getId());
            return this.toDeviceDTO(this.dataProvider.saveDevice(newDevice));
        } catch (Exception ex) {
            throw new GenericErrorException(ex.getMessage());
        }
    }

    @Override
    public DeviceDTO patchDevice(Long id, CreateDeviceDTO request) {
        try {
            Device oldDevice = this.getSingleDevice(id);
            Device newDevice = this.toDevice(request);
            return this.toDeviceDTO(this.dataProvider.saveDevice(oldDevice.update(newDevice)));
        } catch (Exception ex) {
            throw new GenericErrorException(ex.getMessage());
        }
    }

    @Override
    public DeviceDTO deleteDevice(Long id) {
        DeviceDTO device = this.getDevice(id);
        this.dataProvider.deleteDevice(id);
        return device;
    }

    private Device getSingleDevice(Long id) {
        try {
            return this.dataProvider.getDevice(id);
        } catch (Exception ex) {
            throw new UserNotFoundException(id);
        }
    }

    public Device toDevice(CreateDeviceDTO dto) {
        return Device.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .time(dto.getTime())
                .build();
    }

    public DeviceDTO toDeviceDTO(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .time(device.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }
}
