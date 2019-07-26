package com.mtn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Object version is not up-to-date")
public class VersionConflictException extends RuntimeException {

    public VersionConflictException(Object object) {
    }

}
