package com.challenge.truphonechallenge.impl;

import com.challenge.truphonechallenge.entity.DeviceEntity;
import com.challenge.truphonechallenge.model.Device;
import com.challenge.truphonechallenge.repository.IDeviceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeviceDataProviderImplTest {

    @Mock
    private IDeviceRepository repo;

    @InjectMocks
    private DeviceDataProviderImpl dataProvider;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListDevices() {
        Page<DeviceEntity> deviceEntities = new PageImpl((Arrays.asList(
                DeviceEntity.builder()
                        .name("Name_1")
                        .brand("Brand_1")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build(),
                DeviceEntity.builder()
                        .name("Name_2")
                        .brand("Brand_2")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build()
                )));

        Mockito.when(repo.findAll(Mockito.any(Pageable.class))).thenReturn(deviceEntities);

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
        Assertions.assertEquals(deviceList, dataProvider.listDevices(PageRequest.of(0, 5), null));
    }

    @Test
    public void testListDevicesByBrand() {
        Page<DeviceEntity> deviceEntities = new PageImpl((Arrays.asList(
                DeviceEntity.builder()
                        .name("Name_1")
                        .brand("Brand_1")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build()
        )));

        Mockito.when(repo.findAllByBrand(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(deviceEntities);

        List<Device> deviceList = Collections.singletonList(
                Device.builder()
                        .name("Name_1")
                        .brand("Brand_1")
                        .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                        .build()
        );
        Assertions.assertEquals(deviceList, dataProvider.listDevices(PageRequest.of(0, 5), "Brand_1"));
    }

    @Test
    public void testGetDevice() {
        DeviceEntity entity = DeviceEntity.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Mockito.when(repo.getById(Mockito.anyLong())).thenReturn(entity);

        Device device = Device.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Assertions.assertEquals(device, dataProvider.getDevice(1L));
    }

    @Test
    public void testSaveDevice() {
        DeviceEntity entity = DeviceEntity.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Mockito.when(repo.saveAndFlush(Mockito.any(DeviceEntity.class))).thenReturn(entity);

        Device device = Device.builder()
                .id(1L)
                .name("Name_1")
                .brand("Brand_1")
                .time(LocalDateTime.parse("2021-01-01T01:01:01"))
                .build();

        Assertions.assertEquals(device, dataProvider.saveDevice(device));
    }

    @Test
    public void testDeleteDevice() {
        this.dataProvider.deleteDevice(1L);
        BDDMockito.verify(this.repo).deleteById(1L);
    }
}