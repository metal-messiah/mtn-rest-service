package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Group;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupView extends AuditingEntityView {

    private String displayName;
    private String description;

    private List<SimpleUserProfileView> members;

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

    public String getDescription() {
        return description;
    }

    public List<SimpleUserProfileView> getMembers() {
        return members;
    }

}
