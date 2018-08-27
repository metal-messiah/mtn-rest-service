package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ExtractionFieldSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractionFieldSetView extends AuditingEntityView {

    private String fieldSetName;

    public ExtractionFieldSetView() {
    }

    public ExtractionFieldSetView(ExtractionFieldSet extractionField) {
        super(extractionField);
        this.fieldSetName = extractionField.getFieldSetName();
    }

    public String getFieldSetName() {
        return fieldSetName;
    }

    public void setFieldSetName(String fieldSetName) {
        this.fieldSetName = fieldSetName;
    }
}
