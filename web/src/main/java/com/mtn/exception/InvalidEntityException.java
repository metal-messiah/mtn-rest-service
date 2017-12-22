package com.mtn.exception;

/**
 * Created by Tyler on 12/22/2017.
 */
public class InvalidEntityException extends RuntimeException {

    private Object entity;

    public InvalidEntityException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
