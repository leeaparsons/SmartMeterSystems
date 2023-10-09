package com.group15.jacksonpollock.service;

import com.group15.jacksonpollock.entity.MpxnData;
import com.group15.jacksonpollock.entity.UptakeDistribution;
import com.group15.jacksonpollock.repo.CommissionedRepo;
import com.group15.jacksonpollock.repo.MpxnDataRepo;
import com.group15.jacksonpollock.repo.UptakeRepo;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class DashboardService {

    private final CommissionedRepo commissionedRepo;
    private final UptakeRepo uptakeRepo;

    private final MpxnDataRepo mpxnDataRepo;
    public DashboardService(CommissionedRepo commissionedRepo, UptakeRepo uptakeRepo, MpxnDataRepo mpxnDataRepo) {
        this.uptakeRepo = uptakeRepo;
        this.commissionedRepo = commissionedRepo;
        this.mpxnDataRepo = mpxnDataRepo;
    }

    public Long returnTotalMeters() {
        return mpxnDataRepo.count();
    }

    public Integer returnTotalUncommissioned() {
        List<MpxnData> uncommissioned = mpxnDataRepo.findNotCommissionedDevices();
        return uncommissioned.size();
    }

    public String returnLowestUptakeArea() {
        List<UptakeDistribution> uptakeDistributions = uptakeRepo.findAll();
        return findLowestUptakeArea(uptakeDistributions);
    }

    public Double returnAverageUptake() {
        List<UptakeDistribution> uptakeDistributions = uptakeRepo.findAll();
        return Math.round(findAverageUptake(uptakeDistributions)*100.0)/100.0;
    }

    public Integer returnAcceptableUptake() {
        List<UptakeDistribution> uptakeDistributions = uptakeRepo.findAll();
        return findAcceptableUptake(uptakeDistributions);
    }

    //Find the number of areas whose uptake is above 70%
    //This can easily be changed if needed.
    public Integer findAcceptableUptake(List<UptakeDistribution> list){
        int[] total = new int[1];
        list.forEach((n) -> {
            if (n.getUptakePercentage() >= 0.8) {
                total[0] += 1;
            }
        });
        return total[0];
    }

    //To do - look into threading
    public Double findAverageUptake(List<UptakeDistribution> list){
        double[] total = new double[1];
        list.forEach((n) -> {
            total[0] += n.getUptakePercentage();
        });
        return total[0] / list.size();
    }

    public String findLowestUptakeArea(List<UptakeDistribution> uptakeList){
        double minimum = Double.MAX_VALUE;
        int position = 0;

        for (int i = 0; i < uptakeList.size(); i++){
            if (uptakeList.get(i).getUptakePercentage() < minimum) {
                minimum = uptakeList.get(i).getUptakePercentage();
                position = i;
            }
        }

        return uptakeList.get(position).getPostcodeArea();

    }
}
