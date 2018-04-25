package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Group;
import com.mtn.model.view.AuditingEntityView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleGroupView {

    private Integer id;
    private String displayName;

    public SimpleGroupView(Group group) {
        this.id = group.getId();
        this.displayName = group.getDisplayName();
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

}
