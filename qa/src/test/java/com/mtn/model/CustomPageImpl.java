package com.mtn.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Taken from https://stackoverflow.com/questions/34099559/how-to-consume-pageentity-response-using-spring-resttemplate
 * as a means to read a Page<T> response using RestTemplate
 */
public class CustomPageImpl<T> extends PageImpl<T> {

    @JsonCreator
    public CustomPageImpl(@JsonProperty("content") List<T> content,
                          @JsonProperty("number") int number,
                          @JsonProperty("size") int size,
                          @JsonProperty("totalElements") Long totalElements) {
        super(content, new PageRequest(number, size), totalElements);
    }
}
