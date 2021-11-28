package com.challenge.truphonechallenge.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Device {

    private Long id;
    private String name;
    private String brand;
    private LocalDateTime time;

    public Device update(Device device) {
        this.setName(device.name != null ? device.name : this.name);
        this.setBrand(device.brand != null ? device.brand : this.brand);
        this.setTime(device.time != null ? device.time : this.time);
        return this;
    }
}
