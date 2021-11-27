package com.challenge.truphonechallenge.repository;

import com.challenge.truphonechallenge.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeviceRepository extends JpaRepository<DeviceEntity, Long> {

}
