package com.group15.databaseuploadservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group15.databaseuploadservice.entity.DataEntry;
import com.group15.databaseuploadservice.entity.DeviceDataEntry;
import com.group15.databaseuploadservice.repo.DatabaseUploadRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class N3rgyService {
    private final DatabaseUploadRepository repository;

    public N3rgyService(DatabaseUploadRepository repository) {
        this.repository = repository;
    }

    public String mockAPICall() {
        try {
            ClassPathResource resource = new ClassPathResource("test_input.json");
            Path filePath = Paths.get(resource.getURI());

            byte[] contentBytes = Files.readAllBytes(filePath);
            String jsonString = new String(contentBytes, StandardCharsets.UTF_8);

            return jsonString;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public void saveAPIData() {
        repository.deleteAll();
        String jsonString = mockAPICall();
        WebClient webClient = WebClient.create("http://10.72.99.55");
        saveToDatabase(jsonString);
        try {
            webClient.get().uri("/calculateUptakePercentage");
            webClient.get().uri("/calculateCommissionedDistribution");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToDatabase(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(jsonString);
            JsonNode result = root.get("result");

            for (JsonNode entry : result) {
                JsonNode devicesNode = entry.get("devices");
                JsonNode propertyNode = devicesNode.get(0).get("propertyFilter");

                String mpxn =  entry.get("mpxn").asText();
                String address = propertyNode.get("addressIdentifier").asText();
                String postCode = propertyNode.get("postCode").asText();
                ArrayList<DeviceDataEntry> devices = new ArrayList<>();

                for (JsonNode device : devicesNode) {

                    String id = device.get("deviceId").asText();
                    String type = device.get("deviceType").asText();
                    String status = device.get("deviceStatus").asText();
                    String manufacturer = device.get("deviceManufacturer").asText();
                    String model = device.get("deviceModel").asText();
                    String firmwareVersion = device.get("deviceFirmwareVersion").asText();
                    String firmwareVersionStatus = device.get("deviceFirmwareVersionStatus").asText();

                    devices.add(new DeviceDataEntry(id, type, status, manufacturer, model, firmwareVersion, firmwareVersionStatus));

                }

                DataEntry dataEntry = new DataEntry(mpxn, address, postCode, devices);
                repository.save(dataEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
