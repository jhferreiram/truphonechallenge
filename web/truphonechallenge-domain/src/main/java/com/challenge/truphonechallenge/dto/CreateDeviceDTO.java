package com.challenge.truphonechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateDeviceDTO {

    private String name;
    private String brand;
    private LocalDateTime time;
}
