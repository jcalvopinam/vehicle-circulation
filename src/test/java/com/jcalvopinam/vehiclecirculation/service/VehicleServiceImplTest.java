package com.jcalvopinam.vehiclecirculation.service;

import com.jcalvopinam.vehiclecirculation.domain.Vehicle;
import com.jcalvopinam.vehiclecirculation.dto.VehicleResponseDTO;
import com.jcalvopinam.vehiclecirculation.utility.ValidationHelperTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceImplTest {

    @Mock
    private PolicyService policyService;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    public void validateAllowedTest() {
        Mockito.when(policyService.getPolicy())
               .thenReturn(ValidationHelperTest.createPolicy());
        final Vehicle vehicle = new Vehicle("ABC-1231", "2020/10/05", "06:00");
        final VehicleResponseDTO validate = vehicleService.validate(vehicle);
        Assertions.assertEquals("It is permitted to circulate in this hours.", validate.getMessage());
    }

    @Test
    public void validateRestrictedTest() {
        Mockito.when(policyService.getPolicy())
               .thenReturn(ValidationHelperTest.createPolicy());
        final Vehicle vehicle = new Vehicle("ABC-1231", "2020/10/05", "08:00");
        final VehicleResponseDTO validate = vehicleService.validate(vehicle);
        Assertions.assertEquals("It is forbidden to drive at this hours.", validate.getMessage());

    }

    @Test
    public void validateWeekendTest() {
        final Vehicle vehicle = new Vehicle("ABC-1231", "2020/10/10", "08:00");
        final VehicleResponseDTO validate = vehicleService.validate(vehicle);
        Assertions.assertEquals("Free circulation on the weekend", validate.getMessage());

    }

}