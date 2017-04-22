package com.mtn.controller;

import com.mtn.correlation.CorrelationIdFilter;
import com.mtn.correlation.CustomHeadersEnabledServletRequest;
import com.mtn.model.view.SimpleErrorResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 4/21/2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(new SimpleErrorResponseView(400, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity badSortParameter(PropertyReferenceException e) {
        return ResponseEntity.status(400).body(new SimpleErrorResponseView(400, String.format("'%s' is not a valid sort value", e.getPropertyName())));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity internalServerError(HttpServletRequest req, Exception e) throws Exception {
        //Allow annotated exceptions to be handled by Spring
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        logger.error(String.format("UNEXPECTED EXCEPTION - %s", getCorrelationId(req)), e);

        return ResponseEntity.status(500).body(new SimpleErrorResponseView(500, "An Unexpected Error Occurred"));
    }

    ////////////////////////////////

    private String getCorrelationId(HttpServletRequest httpServletRequest) {
        CustomHeadersEnabledServletRequest enhancedRequest = (CustomHeadersEnabledServletRequest) httpServletRequest;
        return enhancedRequest.getHeader(CorrelationIdFilter.MTN_CORRELATION_ID_HEADER);
    }
}
