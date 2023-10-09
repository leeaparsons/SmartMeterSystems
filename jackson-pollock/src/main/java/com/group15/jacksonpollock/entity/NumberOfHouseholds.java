package com.group15.jacksonpollock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Document(collection="number_of_households")
public class NumberOfHouseholds {
    private String postcode;
    private Integer number;
}

