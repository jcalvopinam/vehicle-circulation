package com.jcalvopinam.vehiclecirculation.exception;

/**
 * PlateNumberException Handler
 */
public class PlateNumberException extends RuntimeException {

    private static final long serialVersionUID = -4153350964501883130L;

    /**
     * Custom exception for the plateNumber.
     *
     * @param message receive a message String.
     */
    public PlateNumberException(final String message) {
        super(message);
    }

}
