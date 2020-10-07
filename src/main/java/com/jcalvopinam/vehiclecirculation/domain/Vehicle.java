package com.jcalvopinam.vehiclecirculation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Domain class representing a vehicle with its attributes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {

    private String plateNumber;
    private String date;
    private String time;

}
