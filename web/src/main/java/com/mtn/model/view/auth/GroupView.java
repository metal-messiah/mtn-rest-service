package com.mtn.model.view.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.auth.Group;
import com.mtn.model.view.SimpleUserProfileView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupView extends SimpleGroupView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    private List<SimpleUserProfileView> members = new ArrayList<>();

    public GroupView() {
    }

    public GroupView(Group group) {
        super(group);

        this.createdBy = new SimpleUserProfileView(group.getCreatedBy());
        this.createdDate = group.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(group.getUpdatedBy());
        this.updatedDate = group.getUpdatedDate();

        this.members = group.getMembers().stream().map(SimpleUserProfileView::new).collect(Collectors.toList());
    }

    public SimpleUserProfileView getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleUserProfileView createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleUserProfileView getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SimpleUserProfileView updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<SimpleUserProfileView> getMembers() {
        return members;
    }

    public void setMembers(List<SimpleUserProfileView> members) {
        this.members = members;
    }
}
