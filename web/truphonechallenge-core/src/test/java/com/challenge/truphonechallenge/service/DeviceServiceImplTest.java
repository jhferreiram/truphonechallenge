package com.challenge.truphonechallenge.service;

import com.challenge.truphonechallenge.dataprovider.IDeviceDataProvider;
import com.challenge.truphonechallenge.dto.CreateDeviceDTO;
import com.challenge.truphonechallenge.model.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceServiceImplTest {

    @Mock
    private IDeviceDataProvider dataProvider;

    @InjectMocks
    private DeviceServiceImpl service;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListDevices() {
        List<Device> deviceList = Arrays.asList(
                Device.builder()
                        .name("Name_1")
                        .brand("Brand_1")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build(),
                Device.builder()
                        .name("Name_2")
                        .brand("Brand_2")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build()
        );

        Mockito.when(dataProvider.listDevices(Mockito.any(PageRequest.class), Mockito.anyString())).thenReturn(deviceList);

        Assertions.assertEquals(deviceList.stream().map(service::toDeviceDTO).collect(Collectors.toList()),
                service.listDevices(0, 5, ""));
    }

    @Test
    public void testListDevicesFilterByBrand() {
        List<Device> deviceList = Arrays.asList(
                Device.builder()
                        .name("Name_1")
                        .brand("Brand_1")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build()
        );

        Mockito.when(dataProvider.listDevices(Mockito.any(PageRequest.class), Mockito.anyString())).thenReturn(deviceList);

        Assertions.assertEquals(deviceList.stream().map(service::toDeviceDTO).collect(Collectors.toList()),
                service.listDevices(0, 5, "Brand_1"));
    }

    @Test
    public void testGetDevice() throws Exception {
        Device device = Device.builder()
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(device);

        Assertions.assertEquals(service.toDeviceDTO(device), this.service.getDevice(1L));

        Mockito
                .when(dataProvider.getDevice(Mockito.anyLong()))
                .thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> {
            this.service.getDevice(2L);
        });
    }

    @Test
    public void testCreateDevice() {
        Device device = Device.builder()
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Mockito.when(dataProvider.saveDevice(Mockito.any(Device.class))).thenReturn(device);

        Assertions.assertEquals(service.toDeviceDTO(device), this.service.createDevice(CreateDeviceDTO.builder().build()));
    }

    @Test
    public void testUpdateDevice() throws Exception {
        Device device = Device.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(device);

        device.setName("Name_1_Changed");
        device.setName("Brand_1_Changed");
        device.setTime(LocalDateTime.parse("2022-01-01T01:01:01"));
        Mockito.when(dataProvider.saveDevice(Mockito.any(Device.class))).thenReturn(device);

        CreateDeviceDTO request = CreateDeviceDTO.builder()
                .name("Name_1_Changed")
                .brand("Brand_1_Changed")
                .time(LocalDateTime.parse("2022-01-01T01:01:01"))
                .build();

        Assertions.assertEquals(service.toDeviceDTO(device), this.service.updateDevice(1L, request));

        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> {
            this.service.updateDevice(2L, request);
        });
    }

    @Test
    public void testPatchDevice() throws Exception {
        Device device = Device.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();
        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(device);

        device.setName("Name_1_Changed");
        Mockito.when(dataProvider.saveDevice(Mockito.any(Device.class))).thenReturn(device);

        CreateDeviceDTO request = CreateDeviceDTO.builder()
                .name("Name_1_Changed")
                .build();

        Assertions.assertEquals(service.toDeviceDTO(device), this.service.patchDevice(1L, request));

        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> {
            this.service.patchDevice(2L, request);
        });
    }

    @Test
    public void testDeleteDevice() throws Exception {
        Device device = Device.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(device);

        this.service.deleteDevice(1L);

        BDDMockito.verify(this.dataProvider).deleteDevice(1L);

        Mockito.when(dataProvider.getDevice(Mockito.anyLong())).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> {
            this.service.deleteDevice(2L);
        });
    }
}