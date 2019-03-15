package com.mtn.exception;

public class InvalidEntityException extends RuntimeException {

    private Object entity;

    public InvalidEntityException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
