package com.jcalvopinam.vehiclecirculation.controller;

import com.jcalvopinam.vehiclecirculation.domain.Vehicle;
import com.jcalvopinam.vehiclecirculation.dto.VehicleResponseDTO;
import com.jcalvopinam.vehiclecirculation.service.VehicleService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class intercept the incoming request.
 */
@RestController
@RequestMapping("/api")
@Api("/api")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Endpoint that receives a Vehicle object to be validated.
     *
     * @param vehicle receives a Vehicle object.
     * @return a VehicleResponseDTO object.
     */
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> validate(@RequestBody final Vehicle vehicle) {
        return new ResponseEntity<>(vehicleService.validate(vehicle), HttpStatus.OK);
    }

}
