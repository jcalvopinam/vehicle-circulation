package com.jcalvopinam.vehiclecirculation.service;

import com.jcalvopinam.vehiclecirculation.domain.Vehicle;
import com.jcalvopinam.vehiclecirculation.dto.VehicleResponseDTO;
import com.jcalvopinam.vehiclecirculation.utility.ValidationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Class responsible for the implementation of the VehicleService interface.
 */
@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final PolicyService policyService;

    public VehicleServiceImpl(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VehicleResponseDTO validate(final Vehicle vehicle) {
        final char lastNumber = ValidationHelper.getLastNumber(vehicle.getPlateNumber());
        final LocalDateTime vehicleDateTime = ValidationHelper.dateTime(vehicle.getDate(), vehicle.getTime());

        VehicleResponseDTO response;

        if (ValidationHelper.isWeekDay(vehicleDateTime.getDayOfWeek())) {
            if (ValidationHelper.isCirculationRestricted(vehicleDateTime, lastNumber, policyService.getPolicy())) {
                response = new VehicleResponseDTO("Restricted hours", "It is forbidden to drive at this hours.");
            } else {
                response = new VehicleResponseDTO("Allowed hours", "It is permitted to circulate in this hours.");
            }
        } else {
            response = new VehicleResponseDTO("Allowed weekend", "Free circulation on the weekend");
        }
        return response;
    }

}
