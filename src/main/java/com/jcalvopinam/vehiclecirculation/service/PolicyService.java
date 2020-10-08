package com.jcalvopinam.vehiclecirculation.service;

import com.jcalvopinam.vehiclecirculation.domain.Policy;

/**
 * Interface in charge of the policy.
 */
public interface PolicyService {

    /**
     * This method will return the different times for the policy retrieving from application.properties file.
     *
     * @return Policy object.
     */
    Policy getPolicy();

}
