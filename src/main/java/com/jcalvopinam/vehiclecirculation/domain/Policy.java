package com.jcalvopinam.vehiclecirculation.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Domain class representing a Policy with its attributes
 */
@Data
@Builder
public class Policy {

    private Time morningStartTime;
    private Time morningEndTime;
    private Time afternoonStartTime;
    private Time afternoonEndTime;

}
