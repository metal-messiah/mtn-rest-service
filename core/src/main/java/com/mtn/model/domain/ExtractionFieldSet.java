package com.mtn.model.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Tyler on 7/9/2018.
 */
@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="field_set_id"))
public class ExtractionFieldSet extends AuditingEntity {

    private Integer id;
    private String fieldSetName;

    private List<ExtractionField> fields;

    public String getFieldSetName() {
        return fieldSetName;
    }

    public void setFieldSetName(String fieldSetName) {
        this.fieldSetName = fieldSetName;
    }

    @ManyToMany
    @JoinTable(
            name = "extraction_field_set_fields",
            joinColumns = @JoinColumn(name = "field_set_id", referencedColumnName = "field_set_id"),
            inverseJoinColumns = @JoinColumn(name = "extraction_field_id", referencedColumnName = "extraction_field_id")
    )
    public List<ExtractionField> getFields() {
        return fields;
    }

    public void setFields(List<ExtractionField> fields) {
        this.fields = fields;
    }
}
