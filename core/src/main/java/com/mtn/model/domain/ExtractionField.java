package com.mtn.model.domain;

import javax.persistence.*;

/**
 * Created by Tyler on 7/5/2018.
 */
@Entity
@Table
public class ExtractionField extends AuditingEntity implements Identifiable {

    private Integer id;
    private String displayName;
    private String fieldMapping;
    private String header;
    private String extractionDataType;

    @Id
    @GeneratedValue
    @Column(name = "extraction_field_id")
    public Integer getId() {
        return id;
    }

    @Override
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
