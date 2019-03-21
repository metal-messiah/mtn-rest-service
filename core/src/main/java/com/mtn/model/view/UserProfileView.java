package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreList;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleGroupView;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.simpleView.SimpleStoreListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileView extends AuditingEntityView {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    // private List<SimpleStoreListView> subscribedStoreLists = new ArrayList<>();;
    // private List<SimpleStoreListView> createdStoreLists = new ArrayList<>();;

    private SimpleGroupView group;
    private SimpleRoleView role;

    public UserProfileView() {
    }

    public UserProfileView(UserProfile userProfile) {
        super(userProfile);

        this.email = userProfile.getEmail();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        this.phoneNumber = userProfile.getPhoneNumber();

        if (userProfile.getGroup() != null) {
            this.group = new SimpleGroupView(userProfile.getGroup());
        }

        if (userProfile.getRole() != null) {
            this.role = new SimpleRoleView(userProfile.getRole());
        }

        // if (userProfile.getSubscribedStoreLists() != null) {
        // this.subscribedStoreLists = userProfile.getSubscribedStoreLists().stream()
        // .filter(sl -> sl.getDeletedDate() == null).map(SimpleStoreListView::new)
        // .collect(Collectors.toList());
        // }

        // if (userProfile.getCreatedStoreLists() != null) {
        // this.createdStoreLists = userProfile.getCreatedStoreLists().stream()
        // .filter(sl -> sl.getDeletedDate() == null).map(SimpleStoreListView::new)
        // .collect(Collectors.toList());
        // }
    }

    // public List<SimpleStoreListView> getSubscribedStoreLists() {
    // return this.subscribedStoreLists;
    // }

    // public void setSubscribedStoreLists(List<SimpleStoreListView>
    // subscribedStoreLists) {
    // this.subscribedStoreLists = subscribedStoreLists;
    // }

    // public List<SimpleStoreListView> getCreatedStoreLists() {
    // return this.createdStoreLists;
    // }

    // public void setCreatedStoreLists(List<SimpleStoreListView> createdStoreLists)
    // {
    // this.createdStoreLists = createdStoreLists;
    // }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SimpleGroupView getGroup() {
        return group;
    }

    public void setGroup(SimpleGroupView group) {
        this.group = group;
    }

    public SimpleRoleView getRole() {
        return role;
    }

    public void setRole(SimpleRoleView role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
