package com.jcalvopinam.vehiclecirculation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Domain class representing a Time with its attributes
 */
@Data
@AllArgsConstructor
public class Time {

    private int hour;
    private int minute;

}
