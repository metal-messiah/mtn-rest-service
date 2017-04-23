package com.mtn.service;

/**
 * Created by Allen on 4/22/2017.
 */
public abstract class ValidatingDataService<T> {

    public abstract void validateForInsert(T object);

    public abstract void validateForUpdate(T object);

    public abstract void validateBusinessRules(T object);

    public abstract void validateNotNull(T object);

    public abstract void validateDoesNotExist(T object);
}
