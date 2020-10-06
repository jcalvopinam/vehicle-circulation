package com.jcalvopinam.vehiclecirculation.exception;

/**
 * TimeException Handler
 */
public class TimeException extends RuntimeException {

    private static final long serialVersionUID = -2834730596349128814L;

    /**
     * Custom exception for the time.
     *
     * @param message receive a message String.
     */
    public TimeException(final String message) {
        super(message);
    }

}
