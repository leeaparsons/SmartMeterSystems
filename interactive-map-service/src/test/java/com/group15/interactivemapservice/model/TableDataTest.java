package com.group15.interactivemapservice.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TableDataTest {

    @Test
    void whenNewTableDataObjectCreateItMapsCorrectly() {
        Device device = new Device("10-22-30-40-11-DE", "ESME", "COMMISSIONED", "1063", "00050203", "38040404", "ACTIVE");
        TableData testing = new TableData("1023536563502", "4 Privet Drive, Surrey", "SU21 3RD", new ArrayList<>(Arrays.asList(device)));

        assertNotNull(testing);
        assertEquals(testing.getMpxn(), "1023536563502");
        assertEquals(testing.getAddress(), "4 Privet Drive, Surrey");
        assertEquals(testing.getPostcode(), "SU21 3RD");

        assertNotNull(testing.getDevices());
        assertEquals(1, testing.getDevices().size());
        assertTrue(testing.getDevices().get(0) instanceof Device);

        Device[] expectedDevices = new Device[]{device};
        Device[] actualDevices = testing.getDevices().toArray(new Device[0]);

        assertArrayEquals(expectedDevices, actualDevices);

    }
}