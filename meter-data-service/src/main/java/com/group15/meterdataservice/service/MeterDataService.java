package com.group15.meterdataservice.service;

import com.group15.meterdataservice.entity.MeterData;
import com.group15.meterdataservice.repo.MeterDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterDataService {

    private final MeterDataRepository repository;

    public MeterDataService(MeterDataRepository repository) {
        this.repository = repository;
    }

    public List<MeterData> findAll() {
        return repository.findAll();
    }

    public List<MeterData> findByPostcodeStartingWith(String postcodeArea) {
        String postcodeAreaFilter = postcodeArea + " ";
        return repository.findByPostcodeStartingWith(postcodeAreaFilter);
    }

    public List<MeterData> findUncommissionedDevices() {
        return repository.findUncommissionedDevices();
    }

    public List<MeterData> findNotCommissionedDevicesByPostcodeStartsWith(String postcodeArea) {
        String postCodeArea = postcodeArea + " ";
        return repository.findNotCommissionedDevicesByPostcodeStartsWith(postCodeArea);
    }

    public List<MeterData> findByMpxn(String mpxn) {
        return repository.findByMpxn(mpxn);
    }
}
