package com.mtn.service;

/**
 * Created by Allen on 4/22/2017.
 */
public interface ValidatingDataService<T> {

    void validateForInsert(T object);

    void validateForUpdate(T object);

    void validateBusinessRules(T object);

    void validateNotNull(T object);
}
