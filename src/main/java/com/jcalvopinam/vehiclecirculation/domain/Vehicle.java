package com.jcalvopinam.vehiclecirculation.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain class representing a vehicle with its attributes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Vehicle {

    @ApiModelProperty(notes = "Number of the plate format: \"3Char-4Digits\"", required = true)
    private String plateNumber;

    @ApiModelProperty(notes = "Date format: \"yyyy/MM/dd\"", required = true)
    private String date;

    @ApiModelProperty(notes = "Time format: \"HH:MM\"", required = true)
    private String time;

}
