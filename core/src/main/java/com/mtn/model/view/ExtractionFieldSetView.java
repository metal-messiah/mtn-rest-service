package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ExtractionField;
import com.mtn.model.domain.ExtractionFieldSet;

import java.io.Serializable;

/**
 * Created by Tyler on 7/9/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractionFieldSetView extends AuditingEntityView {

    private String fieldSetName;

    public ExtractionFieldSetView(ExtractionFieldSet extractionField) {
        super(extractionField);

        this.fieldSetName = extractionField.getFieldSetName();
    }

    public String getFieldSetName() {
        return fieldSetName;
    }

}
