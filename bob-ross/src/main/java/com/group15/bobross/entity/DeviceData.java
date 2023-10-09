package com.group15.bobross.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeviceData {
    private String deviceId;
    private String deviceType;
    private String deviceStatus;
    private String deviceManufacturer;
    private String deviceModel;
    private String deviceFirmwareVersion;
    private String deviceFirmwareVersionStatus;
}
