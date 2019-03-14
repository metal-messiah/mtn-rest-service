package com.mtn.exception;

public class DeletedEntityReactivationException extends RuntimeException {

    private Object entity;

    public DeletedEntityReactivationException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
