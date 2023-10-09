package com.group15.jacksonpollock;

import com.group15.jacksonpollock.entity.UptakeDistribution;
import com.group15.jacksonpollock.repo.CommissionedRepo;
import com.group15.jacksonpollock.repo.MpxnDataRepo;
import com.group15.jacksonpollock.repo.UptakeRepo;
import com.group15.jacksonpollock.service.DashboardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardServiceUnitTests {

    private static UptakeRepo uptakeRepo;

    private static CommissionedRepo commissionedRepo;
    private static MpxnDataRepo mpxnDataRepo;
    private static DashboardService service;


    @BeforeAll
    static void setService(){
        service = new DashboardService(commissionedRepo, uptakeRepo, mpxnDataRepo);
    }

    @Test
    public void testAverageUptake(){
        ArrayList<UptakeDistribution> test = new ArrayList<>();
        test.add(new UptakeDistribution("AL1", 0.6));
        test.add(new UptakeDistribution("AL10", 0.9));
        test.add(new UptakeDistribution("AL2", 0.3));

        assertEquals(0.6, service.findAverageUptake(test));
    }

    @Test
    public void testAcceptableUptake(){
        ArrayList<UptakeDistribution> test = new ArrayList<>();
        test.add(new UptakeDistribution("AL1", 0.6));
        test.add(new UptakeDistribution("AL10", 0.9));
        test.add(new UptakeDistribution("AL2", 0.3));

        //Let's establish a baseline
        assertEquals(1, service.findAcceptableUptake(test));

        test.add(new UptakeDistribution("B10", 0.9));

        //Do things break if the list changes?
        assertEquals(2, service.findAcceptableUptake(test));
    }

    @Test
    public void testLowestUptake(){
        ArrayList<UptakeDistribution> test = new ArrayList<>();
        test.add(new UptakeDistribution("AL1", 0.6));
        test.add(new UptakeDistribution("AL10", 0.9));
        test.add(new UptakeDistribution("AL2", 0.3));

        //Let's establish a baseline
        assertEquals("AL2", service.findLowestUptakeArea(test));

        test.add(new UptakeDistribution("B10", 0.2));

        //Do things break if the list changes?
        assertEquals("B10", service.findLowestUptakeArea(test));
    }
}
