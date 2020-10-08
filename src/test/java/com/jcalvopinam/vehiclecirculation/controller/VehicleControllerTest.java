package com.jcalvopinam.vehiclecirculation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcalvopinam.vehiclecirculation.domain.Vehicle;
import com.jcalvopinam.vehiclecirculation.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleControllerTest {

    @MockBean
    private VehicleService storageService;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void validateTest() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                                               .build();
        final ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/api")
                                                      .accept(MediaType.APPLICATION_JSON)
                                                      .content(objectAsJson(createVehicleRequest()))
                                                      .contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk());
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn()
                                                         .getResponse()
                                                         .getStatus(), "It should be 200");
    }

    private Vehicle createVehicleRequest() {
        final Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("AAA-0001");
        vehicle.setDate("2020/10/05");
        vehicle.setTime("07:00");
        return vehicle;
    }

    private String objectAsJson(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}