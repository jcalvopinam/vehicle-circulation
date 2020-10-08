package com.jcalvopinam.vehiclecirculation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VehicleCirculationApplicationTests {

    @Test
    public void contextLoadsTest() {
        VehicleCirculationApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }

}
