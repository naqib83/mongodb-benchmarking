package com.mongodb.benchmarking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "address")
public class Address {
    private String street;
    private String city;
    private String state;
    private int zip;
}
