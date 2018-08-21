package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ExtractionField;

/**
 * Created by Tyler on 7/5/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractionFieldView extends AuditingEntityView {

    private String displayName;
    private String fieldMapping;
    private String header;
    private String extractionDataType;

    public ExtractionFieldView(ExtractionField extractionField) {
        super(extractionField);

        this.displayName = extractionField.getDisplayName();
        this.fieldMapping = extractionField.getFieldMapping();
        this.header = extractionField.getHeader();
        this.extractionDataType = extractionField.getExtractionDataType();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFieldMapping() {
        return fieldMapping;
    }

    public String getHeader() {
        return header;
    }

    public String getExtractionDataType() {
        return extractionDataType;
    }

}
