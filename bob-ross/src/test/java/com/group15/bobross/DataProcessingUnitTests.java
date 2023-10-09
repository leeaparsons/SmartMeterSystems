package com.group15.bobross;

import com.group15.bobross.entity.NumberOfHouseholds;
import com.group15.bobross.repo.CommissionedRepo;
import com.group15.bobross.repo.HouseholdDataRepo;
import com.group15.bobross.repo.MpxnDataRepo;
import com.group15.bobross.repo.UptakeRepo;
import com.group15.bobross.service.DataProcessingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DataProcessingUnitTests {

    private static MpxnDataRepo mpxnRepo;
    private static HouseholdDataRepo householdRepo;

    private static UptakeRepo uptakeRepo;

    private static CommissionedRepo commissionedRepo;
    private static DataProcessingService service;


    @BeforeAll
    static void setService(){
        service = new DataProcessingService(mpxnRepo, householdRepo, uptakeRepo, commissionedRepo);
    }

    //Make sure we can read from a file correctly.
    @Test
    public void readValidFile() {
        //Line breaks in the JSON object cause some issues.
        //I am just going to test that the object contains the relevant data, and not a random extra postcode.
        assertThat(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")), containsString("\"postcode\": \"AL1\""));
        assertThat(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")), containsString("\"postcode\": \"AL10\""));
        assertThat(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")), containsString("\"postcode\": \"AL2\""));
        assertThat(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")), containsString("\"totalResidents\": 15573"));
        assertThat(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")), containsString("\"totalResidents\": 13247"));
        assertThat(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")), containsString("\"totalResidents\": 9823"));
        assertFalse(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")).contains("\"totalResidents\": Should not be here!"));
        assertFalse(service.readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json")).contains("\"postcode\": \"Should not be here!\""));
    }

    //Make sure that if a file doesn't exist, the correct error is thrown.
    @Test
    public void readInvalidFile() {
        assertEquals(service.readHouseholdsFromJson(new ClassPathResource("badFile.json")), "Error reading file");
    }

    //Make sure the object array list is being created correctly.
    @Test
    public void createHouseholdArrayList() {
        ArrayList<NumberOfHouseholds> test = new ArrayList<>();
        test.add(new NumberOfHouseholds("AL1", 15573));
        test.add(new NumberOfHouseholds("AL10", 13247));
        test.add(new NumberOfHouseholds("AL2", 9823));

        assertEquals(test.get(0).getPostcode(), service.getNumberOfHouseholds().get(0).getPostcode());
        assertEquals(test.get(1).getPostcode(), service.getNumberOfHouseholds().get(1).getPostcode());
        assertEquals(test.get(2).getPostcode(), service.getNumberOfHouseholds().get(2).getPostcode());

        assertEquals(test.get(0).getNumber(), service.getNumberOfHouseholds().get(0).getNumber());
        assertEquals(test.get(1).getNumber(), service.getNumberOfHouseholds().get(1).getNumber());
        assertEquals(test.get(2).getNumber(), service.getNumberOfHouseholds().get(2).getNumber());
    }
}
