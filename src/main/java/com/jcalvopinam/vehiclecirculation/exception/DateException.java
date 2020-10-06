package com.jcalvopinam.vehiclecirculation.exception;

/**
 * DateException Handler
 */
public class DateException extends RuntimeException {

    private static final long serialVersionUID = -5690318489488979814L;

    /**
     * Custom exception for the date.
     *
     * @param message receive a message String.
     */
    public DateException(final String message) {
        super(message);
    }

}
