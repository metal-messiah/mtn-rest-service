package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.simpleView.SimpleErrorResponseView;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConflictErrorResponseView extends SimpleErrorResponseView {

    private Object current;

    public ConflictErrorResponseView(Object current) {
        super(HttpStatus.CONFLICT, "Submitted version is not up-to-date. Resolve conflicts, update the version, and try again.");
        this.current = current;
    }

    public Object getCurrent() {
        return current;
    }

    public void setCurrent(Object current) {
        this.current = current;
    }
}
