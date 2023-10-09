package com.group15.meterdataservice;

import com.group15.meterdataservice.entity.Device;
import com.group15.meterdataservice.entity.MeterData;
import com.group15.meterdataservice.repo.MeterDataRepository;
import com.group15.meterdataservice.service.MeterDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", authorities="ADMIN")
@ActiveProfiles("test")
public class MeterDataTests {

    @Mock
    private MeterDataRepository repository;
    @InjectMocks
    private MeterDataService service;
    MeterData meterData;
    ArrayList<Device> devices;

    @BeforeEach
    public void setup() {
        devices = new ArrayList<>();
        devices.add(new Device("10-22-30-40-11-DE", "ESME", "COMMISSIONED", "1063", "00050203", "38040404", "ACTIVE"));
        devices.add(new Device("00-00-00-00-00-TE", "TEST", "NOT COMMISSIONED", "0001", "00050204", "38040404", "ACTIVE"));
        meterData = new MeterData("MPXN123", "123 TEST", "TEST 123", devices);
        MockitoAnnotations.openMocks(this);
        service = new MeterDataService(repository);
    }

    @AfterEach
    public void clearList() {
        devices.clear();
    }

    @Test
    void testFindAll() {
        List<MeterData> expectedMeterDataList = new ArrayList<>();
        expectedMeterDataList.add(meterData);
        when(repository.findAll()).thenReturn(expectedMeterDataList);
        List<MeterData> actualMeterDataList = service.findAll();
        assertEquals(expectedMeterDataList, actualMeterDataList);
    }

    @Test
    void testFindUncommissionedDevices() {
        List<MeterData> expectedMeterDataList = new ArrayList<>();
        expectedMeterDataList.add(meterData);
        when(repository.findUncommissionedDevices()).thenReturn(expectedMeterDataList);
        List<MeterData> actualMeterDataList = service.findUncommissionedDevices();
        assertEquals(expectedMeterDataList, actualMeterDataList);
    }

    @Test
    void testFindNotCommissionedDevicesByPostcodeStartsWith() {
        String postcodeArea = "TEST";
        List<MeterData> expectedMeterDataList = new ArrayList<>();
        expectedMeterDataList.add(meterData);
        when(repository.findNotCommissionedDevicesByPostcodeStartsWith(postcodeArea + " ")).thenReturn(expectedMeterDataList);
        List<MeterData> actualMeterDataList = service.findNotCommissionedDevicesByPostcodeStartsWith(postcodeArea);
        assertEquals(expectedMeterDataList, actualMeterDataList);
    }

}