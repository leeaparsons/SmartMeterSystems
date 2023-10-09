package com.group15.jacksonpollock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@Document(collection="uptake_distribution")
public class UptakeDistribution {
    private String postcodeArea;
    private Double uptakePercentage;
}
