package com.jcalvopinam.vehiclecirculation.service;

import com.jcalvopinam.vehiclecirculation.domain.Vehicle;
import com.jcalvopinam.vehiclecirculation.dto.VehicleResponseDTO;

/**
 * Interface in charge of vehicle validation.
 */
public interface VehicleService {

    /**
     * This method will return if a vehicle can be on the road or not depending on the plate, date and time.
     *
     * @param vehicle receives a Vehicle object.
     * @return a VehicleResponseDTO object.
     */
    VehicleResponseDTO validate(Vehicle vehicle);

}
