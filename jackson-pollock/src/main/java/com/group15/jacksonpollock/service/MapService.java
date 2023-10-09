package com.group15.jacksonpollock.service;

import com.group15.jacksonpollock.entity.CommissionedDistribution;
import com.group15.jacksonpollock.entity.UptakeDistribution;
import com.group15.jacksonpollock.repo.CommissionedRepo;
import com.group15.jacksonpollock.repo.UptakeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    private final CommissionedRepo commissionedRepo;
    private final UptakeRepo uptakeRepo;

    public MapService(CommissionedRepo commissionedRepo, UptakeRepo uptakeRepo) {
        this.uptakeRepo = uptakeRepo;
        this.commissionedRepo = commissionedRepo;
    }

    public List<UptakeDistribution> returnUptakeData() {
        return uptakeRepo.findAll();
    }

    public List<CommissionedDistribution> returnCommissionedData() {
        return commissionedRepo.findAll();
    }

}
