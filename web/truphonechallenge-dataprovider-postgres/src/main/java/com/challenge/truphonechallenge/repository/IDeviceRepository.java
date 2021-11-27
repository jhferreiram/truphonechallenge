package com.challenge.truphonechallenge.repository;

import com.challenge.truphonechallenge.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Page<DeviceEntity> findAllByBrand(String brand, Pageable pageable);
}
