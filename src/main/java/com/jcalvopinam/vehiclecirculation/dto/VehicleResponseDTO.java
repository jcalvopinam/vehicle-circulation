package com.jcalvopinam.vehiclecirculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Representation class to show whether the vehicle is roadworthy
 */
@Data
@AllArgsConstructor
@ToString
public class VehicleResponseDTO {

    private String type;
    private String message;

}
