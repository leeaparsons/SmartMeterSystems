package com.group15.jacksonpollock.controller;

import com.group15.jacksonpollock.service.DashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping(path="/paintDashboard/total")
    public Long paintDashboardTotal() {
        return dashboardService.returnTotalMeters();
    }

    @GetMapping(path="/paintDashboard/totalUncommissioned")
    public Integer paintDashboardTotalUncommissioned() {
        return dashboardService.returnTotalUncommissioned();
    }

    @GetMapping(path="/paintDashboard/lowestUptakeArea")
    public String paintDashboardLowestUptakeArea() {
        return dashboardService.returnLowestUptakeArea();
    }

    @GetMapping(path="/paintDashboard/averagePercentageUptake")
    public Double paintDashboardAveragePercentageUptake() {
        return dashboardService.returnAverageUptake();
    }

    @GetMapping(path="/paintDashboard/acceptableUptake")
    public Integer paintDashboardAcceptableUptake() {
        return dashboardService.returnAcceptableUptake();
    }
}
