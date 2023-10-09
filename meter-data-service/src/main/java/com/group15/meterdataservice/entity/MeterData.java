package com.group15.meterdataservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
@Document(collection="test_meter_data")
public class MeterData {
    private String mpxn;
    private String address;
    private String postcode;
    private ArrayList<Device> devices;
}
