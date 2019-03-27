package com.mtn.exception;

public class VersionConflictException extends RuntimeException {

    private Object object;

    public VersionConflictException(Object object) {
        super("Object version is not up-to-date");
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
