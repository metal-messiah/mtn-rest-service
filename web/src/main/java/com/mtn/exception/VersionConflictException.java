package com.mtn.exception;

/**
 * Created by Allen on 4/23/2017.
 */
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
