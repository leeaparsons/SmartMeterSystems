package com.group15.meterdataservice.controller;

import com.group15.meterdataservice.entity.MeterData;
import com.group15.meterdataservice.service.MeterDataService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MeterDataController {

    private final MeterDataService meterDataService;

    public MeterDataController (MeterDataService meterDataService) {
        this.meterDataService = meterDataService;
    }

    @GetMapping(path="/")
    public List<MeterData> pullDatabase()
    {
        return meterDataService.findAll();
    }

    @GetMapping(path="/uncommissioned")
    public List<MeterData> filterCommissioned()
        {
            return meterDataService.findUncommissionedDevices();
        }

    @GetMapping(path="devices/{postcodeArea}")
    public List <MeterData> findByPostcodeStartingWith(@PathVariable String postcodeArea){
        return meterDataService.findByPostcodeStartingWith(postcodeArea);
    }

    @GetMapping(path="devices/mpxn/{mpxn}")
    public List <MeterData> findByMpxn(@PathVariable String mpxn){
        return meterDataService.findByMpxn(mpxn);
    }

    @GetMapping(path="/devices/{area}/uncommissioned")
    public List<MeterData> findUncommissionedByPostcodeStartsWith(@PathVariable String area) {
        return meterDataService.findNotCommissionedDevicesByPostcodeStartsWith(area);
    }
}
