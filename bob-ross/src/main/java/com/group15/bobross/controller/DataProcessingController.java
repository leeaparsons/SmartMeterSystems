package com.group15.bobross.controller;
import com.group15.bobross.entity.MpxnData;
import com.group15.bobross.service.DataProcessingService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class DataProcessingController {

    private final DataProcessingService dataProcessingService;

    public DataProcessingController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @GetMapping(path="/returnAllData")
    public List<MpxnData> returnAllData() {
        return dataProcessingService.returnAllMpxnData();
    }

    @GetMapping(path="/calculateUptakePercentage")
    public String calculateUptakePercentage() {
        return dataProcessingService.calculateUptakePercentage();
    }

    @GetMapping(path="/calculateCommissionedDistribution")
    public String calculateCommissionedDistribution() {
        return dataProcessingService.calculateCommissionedDistribution();
    }

}


