package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ExtractionField;

import java.io.Serializable;

/**
 * Created by Tyler on 7/5/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractionFieldView extends AuditingEntityView implements Serializable {

    private Integer id;
    private String displayName;
    private String fieldMapping;
    private String header;
    private String extractionDataType;

    public ExtractionFieldView(ExtractionField extractionField) {
        super(extractionField);

        this.id = extractionField.getId();
        this.displayName = extractionField.getDisplayName();
        this.fieldMapping = extractionField.getFieldMapping();
        this.header = extractionField.getHeader();
        this.extractionDataType = extractionField.getExtractionDataType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(String fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getExtractionDataType() {
        return extractionDataType;
    }

    public void setExtractionDataType(String extractionDataType) {
        this.extractionDataType = extractionDataType;
    }

}
