package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ExtractionField;
import com.mtn.model.domain.ExtractionFieldSet;

import java.io.Serializable;

/**
 * Created by Tyler on 7/9/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractionFieldSetView extends AuditingEntityView implements Serializable {

    private Integer id;
    private String fieldSetName;

    public ExtractionFieldSetView(ExtractionFieldSet extractionField) {
        super(extractionField);

        this.id = extractionField.getId();
        this.fieldSetName = extractionField.getFieldSetName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldSetName() {
        return fieldSetName;
    }

    public void setFieldSetName(String fieldSetName) {
        this.fieldSetName = fieldSetName;
    }
}
