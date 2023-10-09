package com.group15.interactivemapservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    @Test
    void whenNewDeviceObjectCreatedItMapsCorrectly() {
        Device testing = new Device("10-22-30-40-11-DE", "ESME", "COMMISSIONED", "1063", "00050203", "38040404", "ACTIVE");
        assertNotNull(testing);
        assertEquals(testing.getDeviceId(), "10-22-30-40-11-DE");
        assertEquals(testing.getDeviceType(), "ESME");
        assertEquals(testing.getDeviceStatus(), "COMMISSIONED");
        assertEquals(testing.getDeviceManufacturer(), "1063");
        assertEquals(testing.getDeviceModel(), "00050203");
        assertEquals(testing.getDeviceFirmwareVersion(), "38040404");
        assertEquals(testing.getDeviceFirmwareVersionStatus(), "ACTIVE");
    }
}