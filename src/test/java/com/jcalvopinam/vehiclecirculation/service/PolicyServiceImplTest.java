package com.jcalvopinam.vehiclecirculation.service;

import com.jcalvopinam.vehiclecirculation.domain.Policy;
import com.jcalvopinam.vehiclecirculation.domain.Time;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceImplTest {

    @InjectMocks
    private PolicyServiceImpl policyService;

    @Test
    public void getPolicy() {
        ReflectionTestUtils.setField(policyService, "morningStartTime", "07:00");
        ReflectionTestUtils.setField(policyService, "morningEndTime", "09:30");
        ReflectionTestUtils.setField(policyService, "afternoonStartTime", "16:00");
        ReflectionTestUtils.setField(policyService, "afternoonEndTime", "19:30");

        final Policy policy = policyService.getPolicy();
        final Time morningStartTime = policy.getMorningStartTime();

        Assertions.assertEquals(7, morningStartTime.getHour());
        Assertions.assertEquals(0, morningStartTime.getMinute());
    }

}