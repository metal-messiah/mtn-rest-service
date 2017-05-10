package com.mtn.exception;

/**
 * Created by Allen on 5/9/2017.
 */
public class DeletedEntityReactivationException extends RuntimeException {

    private Object entity;

    public DeletedEntityReactivationException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
