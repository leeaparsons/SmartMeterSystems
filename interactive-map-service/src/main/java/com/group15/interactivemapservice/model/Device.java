package com.group15.interactivemapservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Device {
    private String deviceId;
    private String deviceType;
    private String deviceStatus;
    private String deviceManufacturer;
    private String deviceModel;
    private String deviceFirmwareVersion;
    private String deviceFirmwareVersionStatus;
}
