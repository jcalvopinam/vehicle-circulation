package com.jcalvopinam.vehiclecirculation.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO to represent the response the generic Exception Response.
 */
@Data
public class ExceptionResponseDTO {

    private String message;
    private String endpoint;
    private LocalDateTime timestamp;

    /**
     * Constructor of ExceptionResponseDTO.
     *
     * @param exception receives the Excetion object.
     * @param endpoint  receives the endpoint where the exception was thrown.
     */
    public ExceptionResponseDTO(final Exception exception, final String endpoint) {
        this.message = exception.getMessage();
        this.endpoint = endpoint;
        this.timestamp = LocalDateTime.now();
    }

}
