package com.mtn.service;

/**
 * Created by Allen on 4/22/2017.
 */
public abstract class ValidatingDataService<T> {

    abstract void validateForInsert(T object);

    abstract void validateForUpdate(T object);

    abstract void validateBusinessRules(T object);

    abstract void validateNotNull(T object);

    abstract void validateDoesNotExist(T object);
}
