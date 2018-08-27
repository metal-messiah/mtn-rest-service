package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Group;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupView extends AuditingEntityView {

    private String displayName;
    private String description;

    private List<SimpleUserProfileView> members;

    public GroupView() {
    }

    public GroupView(Group group) {
        super(group);

        this.displayName = group.getDisplayName();
        this.description = group.getDescription();
        this.members = group.getMembers().stream()
                .filter(member -> member.getDeletedDate() == null)
                .map(SimpleUserProfileView::new)
                .collect(Collectors.toList());
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SimpleUserProfileView> getMembers() {
        return members;
    }

    public void setMembers(List<SimpleUserProfileView> members) {
        this.members = members;
    }
}
