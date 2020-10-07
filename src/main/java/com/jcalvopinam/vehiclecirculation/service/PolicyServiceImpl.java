package com.jcalvopinam.vehiclecirculation.service;

import com.jcalvopinam.vehiclecirculation.domain.Policy;
import com.jcalvopinam.vehiclecirculation.domain.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class responsible for the mapping of the time policy retrieving from application.properties file.
 */
@Service
public class PolicyServiceImpl implements PolicyService {

    @Value("${policy.morning.start.time}")
    private String morningStartTime;

    @Value("${policy.morning.end.time}")
    private String morningEndTime;

    @Value("${policy.afternoon.start.time}")
    private String afternoonStartTime;

    @Value("${policy.afternoon.end.time}")
    private String afternoonEndTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public Policy getPolicy() {
        return Policy.builder()
                     .morningStartTime(getTime(morningStartTime))
                     .morningEndTime(getTime(morningEndTime))
                     .afternoonStartTime(getTime(afternoonStartTime))
                     .afternoonEndTime(getTime(afternoonEndTime))
                     .build();
    }

    /**
     * Retrieves the Time object splitting the hours and minutes.
     *
     * @param time receives the time in a String.
     * @return a Time object.
     */
    private Time getTime(final String time) {
        final String[] morning = time.split(":");
        return new Time(Integer.parseInt(morning[0]), Integer.parseInt(morning[1]));
    }

}
