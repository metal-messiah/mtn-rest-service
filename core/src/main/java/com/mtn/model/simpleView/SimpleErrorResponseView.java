package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleErrorResponseView {

    private Integer code;
    private String message;

    public SimpleErrorResponseView() {
    }

    public SimpleErrorResponseView(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public SimpleErrorResponseView(HttpStatus httpStatus, String message) {
        this(httpStatus.value(), message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
