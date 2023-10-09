package com.group15.jacksonpollock.controller;

import com.group15.jacksonpollock.entity.CommissionedDistribution;
import com.group15.jacksonpollock.entity.UptakeDistribution;
import com.group15.jacksonpollock.service.MapService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping(path="/paintMap/commissioned")
    public List<CommissionedDistribution> paintMapCommissioned() {
        return mapService.returnCommissionedData();
    }

    @GetMapping(path="/paintMap/uptake")
    public List<UptakeDistribution> paintMapUptake() {
        return mapService.returnUptakeData();
    }

}