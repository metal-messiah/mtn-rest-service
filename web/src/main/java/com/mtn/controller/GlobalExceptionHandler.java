package com.mtn.controller;

import com.mtn.correlation.CorrelationIdFilter;
import com.mtn.correlation.CustomHeadersEnabledServletRequest;
import com.mtn.model.simpleView.SimpleErrorResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity resourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleErrorResponseView(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity entityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleErrorResponseView(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SimpleErrorResponseView(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(PropertyReferenceException.class)
//    public ResponseEntity badSortParameter(PropertyReferenceException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SimpleErrorResponseView(HttpStatus.BAD_REQUEST, String.format("'%s' is not a valid sort value", e.getPropertyName())));
//    }
//
//    @ResponseStatus(HttpStatus.CONFLICT)
//    @ExceptionHandler(VersionConflictException.class)
//    public ResponseEntity conflict(VersionConflictException e) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictErrorResponseView(e.getObject()));
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({SecurityException.class, AccessDeniedException.class})
//    public void notAuthorized() {
//    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity internalServerError(HttpServletRequest req, Exception e) throws Exception {
//        //Allow annotated exceptions to be handled by Spring
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
//            throw e;
//        }
//
//        logger.error(String.format("UNEXPECTED EXCEPTION - %s", getCorrelationId(req)), e);
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponseView(HttpStatus.INTERNAL_SERVER_ERROR, "An Unexpected Error Occurred"));
//    }

    ////////////////////////////////

    private String getCorrelationId(HttpServletRequest httpServletRequest) {
        CustomHeadersEnabledServletRequest enhancedRequest = (CustomHeadersEnabledServletRequest) httpServletRequest;
        return enhancedRequest.getHeader(CorrelationIdFilter.MTN_CORRELATION_ID_HEADER);
    }
}
