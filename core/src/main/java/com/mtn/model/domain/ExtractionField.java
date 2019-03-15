package com.mtn.model.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="extraction_field_id"))
public class ExtractionField extends AuditingEntity {

    private String displayName;
    private String fieldMapping;
    private String header;
    private String extractionDataType;

    private List<ExtractionFieldSet> fieldSets;

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

    @ManyToMany(mappedBy = "fields")
    public List<ExtractionFieldSet> getFieldSets() {
        return fieldSets;
    }

    public void setFieldSets(List<ExtractionFieldSet> fieldSets) {
        this.fieldSets = fieldSets;
    }
}
