package com.jcalvopinam.vehiclecirculation.exception;

import com.jcalvopinam.vehiclecirculation.dto.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * This class capture all the exceptions.
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Creates the response from WebRequest object to be shown.
     *
     * @param exception receives the Exception object.
     * @param request   receives the HttpServletRequest object.
     * @return ExceptionResponseDTO object.
     */
    private ResponseEntity<Object> exceptionResponse(final Exception exception, final WebRequest request) {
        log.error("Error Handling [{}]: {}", exception.getClass(), exception.getMessage());
        final String requestURI = ((ServletWebRequest) request).getRequest()
                                                               .getRequestURI();
        return new ResponseEntity<>(new ExceptionResponseDTO(exception, requestURI), HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates the response from HttpServletRequest object to be shown.
     *
     * @param exception  receives the Exception object.
     * @param request    receives the HttpServletRequest object.
     * @param httpStatus receives the HttpStatus object.
     * @return ExceptionResponseDTO object.
     */
    protected ResponseEntity<ExceptionResponseDTO> exceptionResponse(final Exception exception,
                                                                     final HttpServletRequest request,
                                                                     final HttpStatus httpStatus) {
        log.error("Error Handling [{}]: {}", exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDTO(exception, request.getRequestURI()), httpStatus);
    }

    /**
     * The generic exceptions will be captured from Exception.class and this will throw INTERNAL_SERVER_ERROR
     *
     * @param request   receives the HttpServletRequest object.
     * @param exception receives the Exception object.
     * @return ExceptionResponseDTO object.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(final HttpServletRequest request,
                                                                final Exception exception) {
        return exceptionResponse(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Custom exceptions will throw BAD_REQUEST
     *
     * @param request   receives the HttpServletRequest object.
     * @param exception receives the Exception object.
     * @return ExceptionResponseDTO object.
     */
    @ExceptionHandler({DateException.class, PlateNumberException.class, TimeException.class})
    public ResponseEntity<ExceptionResponseDTO> handleDateException(final HttpServletRequest request,
                                                                    final Exception exception) {
        return exceptionResponse(exception, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Customize the response for HttpMessageNotReadableException.
     *
     * @param request   receives the HttpServletRequest object.
     * @param exception receives the Exception object.
     * @return ExceptionResponseDTO object.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        return exceptionResponse(exception, request);
    }

    /**
     * Customize the response for MethodArgumentNotValidException.
     *
     * @param request   receives the HttpServletRequest object.
     * @param exception receives the Exception object.
     * @return ExceptionResponseDTO object.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        return exceptionResponse(exception, request);
    }

    /**
     * Customize the response for ServletRequestBindingException.
     *
     * @param request   receives the HttpServletRequest object.
     * @param exception receives the Exception object.
     * @return ExceptionResponseDTO object.
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            final ServletRequestBindingException exception, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        return exceptionResponse(exception, request);
    }

    /**
     * Customize the response for MissingServletRequestPartException.
     *
     * @param request   receives the HttpServletRequest object.
     * @param exception receives the Exception object.
     * @return ExceptionResponseDTO object.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            final MissingServletRequestPartException exception, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        return exceptionResponse(exception, request);
    }

}