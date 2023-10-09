package com.group15.bobross.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group15.bobross.entity.CommissionedDistribution;
import com.group15.bobross.entity.MpxnData;
import com.group15.bobross.entity.NumberOfHouseholds;
import com.group15.bobross.entity.UptakeDistribution;
import com.group15.bobross.repo.CommissionedRepo;
import com.group15.bobross.repo.HouseholdDataRepo;
import com.group15.bobross.repo.MpxnDataRepo;
import com.group15.bobross.repo.UptakeRepo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class DataProcessingService {

    private final MpxnDataRepo mpxnRepo;
    private final HouseholdDataRepo householdRepo;

    private final UptakeRepo uptakeRepo;

    private final CommissionedRepo commissionedRepo;

    public DataProcessingService(MpxnDataRepo mpxnRepo, HouseholdDataRepo householdRepo, UptakeRepo uptakeRepo, CommissionedRepo commissionedRepo) {
        this.mpxnRepo = mpxnRepo;
        this.householdRepo = householdRepo;
        this.uptakeRepo = uptakeRepo;
        this.commissionedRepo = commissionedRepo;
    }

    //Dump from the database.
    public List<MpxnData> returnAllMpxnData() {
        return mpxnRepo.findAll();
    }

    //Saves the uptake percentage (number of MPXNs vs total households) nationally.
    public String calculateUptakePercentage() {
        try {
            List<NumberOfHouseholds> postcodeNumbers = Collections.synchronizedList(getNumberOfHouseholds());
            List<UptakeDistribution> uptakeDistributions = Collections.synchronizedList(new ArrayList<>());
            Random rand = new Random();

            postcodeNumbers.parallelStream().forEach((n) -> {
//                int num = mpxnRepo.countByPostcodeStartingWith(n.getPostcode() + " ");
//                Double ratio = Double.valueOf(num) / n.getNumber();
//
//                //This actually works, but because of the size of the data set, it saves everything as 0.
//                //Comment out when (if) we ever get a real data set.
//                uptakeDistributions.add(new UptakeDistribution(n.getPostcode(), ratio));

                //Randomise!
                double randomDouble = rand.nextDouble();
                final DecimalFormat df = new DecimalFormat("0.00");
                uptakeDistributions.add(new UptakeDistribution(n.getPostcode(), Math.round(randomDouble*100.0)/100.0));
            });

            uptakeRepo.deleteAll();
            uptakeRepo.saveAll(uptakeDistributions);

            return "Data successfully updated. Go paint some happy little trees.";
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return null;
    }

    public String calculateCommissionedDistribution() {
        //Quick and dirty way to get a list of all the postcodes
        List<NumberOfHouseholds> postcodes = Collections.synchronizedList(getNumberOfHouseholds());
        List<CommissionedDistribution> commissionedDistributions = Collections.synchronizedList(new ArrayList<>());
        Random rand = new Random();

        try {
            postcodes.parallelStream().forEach((n) -> {
//                Double ratio = Double.valueOf(0);
//                List<MpxnData> mpxnDataList = mpxnRepo.findNotCommissionedDevicesByPostcodeStartsWith(n.getPostcode());
//                Integer countUncommissioned = mpxnDataList.size();
//                Integer countTotal = mpxnRepo.countByPostcodeStartingWith(n.getPostcode());
//
//                //This actually works, but because of the size of the data set, it saves everything as 0.
//                //Comment out when (if) we ever get a real data set.
//                if (countTotal == 0){
//                    //Needed to prevent divide by 0 errors.
//                    ratio = (double) 0;
//                } else {
//                    ratio = Double.valueOf(countUncommissioned) / countTotal;
//                }
//                //This actually works, but because of the size of the data set, it saves everything as 0.
//                //Comment out when (if) we ever get a real data set.
//                commissionedDistributions.add(new CommissionedDistribution(n.getPostcode(), ratio));

                //Randomise!
                double randomDouble = rand.nextDouble();
                final DecimalFormat df = new DecimalFormat("0.00");
                commissionedDistributions.add(new CommissionedDistribution(n.getPostcode(), Math.round(randomDouble*100.0)/100.0));
            });

            commissionedRepo.deleteAll();
            commissionedRepo.saveAll(commissionedDistributions);

            return "Data successfully updated. Go paint some happy little trees.";
        }  catch (Exception e)  {
            e.printStackTrace();
        }
        return null;
    }

    //Reads the total number of households in the country from file.
    //Could cause some issues are the data is from 2011 and inevitably more households have
    //come into being since then. Not an issue with current dataset; however.
    public ArrayList<NumberOfHouseholds> getNumberOfHouseholds() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<NumberOfHouseholds> postcodeNumbers = new ArrayList<>();

        String jsonString = readHouseholdsFromJson(new ClassPathResource("numberOfHouseholds.json"));

        try {
            JsonNode root = objectMapper.readTree(jsonString);

            //Unfortunately, this cannot be executed in parallel, due to the use of the JsonNode object.
            for (JsonNode entry : root) {
                String postcode = entry.get("postcode").asText();
                JsonNode households = entry.get("totalResidents");

                postcodeNumbers.add(new NumberOfHouseholds(String.valueOf(postcode), Integer.parseInt(String.valueOf(households))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return postcodeNumbers;
    }

    //Reads a CSV file and returns a string (to be mapped to a JSON object)
    public String readHouseholdsFromJson(ClassPathResource resource){
        try {
            Path filePath = Paths.get(resource.getURI());

            byte[] contentBytes = Files.readAllBytes(filePath);

            return new String(contentBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error reading file";
        }
    }


}
