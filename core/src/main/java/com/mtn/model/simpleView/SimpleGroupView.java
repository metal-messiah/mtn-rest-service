package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Group;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleGroupView extends SimpleAuditingEntityView {

    private String displayName;

    public SimpleGroupView() {
    }

    public SimpleGroupView(Group group) {
        super(group);
        this.displayName = group.getDisplayName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
