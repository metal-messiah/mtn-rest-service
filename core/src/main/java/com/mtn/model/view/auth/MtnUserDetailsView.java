package com.mtn.model.view.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.MtnUserDetails;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 5/11/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MtnUserDetailsView {

    private String email;
    private String firstName;
    private String lastName;

    private List<String> permissions = new ArrayList<>();

    public MtnUserDetailsView() {
    }

    public MtnUserDetailsView(MtnUserDetails mtnUserDetails) {
        this.email = mtnUserDetails.getEmail();
        this.firstName = mtnUserDetails.getFirstName();
        this.lastName = mtnUserDetails.getLastName();

        mtnUserDetails.getAuthorities().forEach(authority -> permissions.add(authority.getAuthority()));
    }

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

    public String getDisplayName() {
        if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)) {
            return String.format("%s %s", firstName, lastName);
        } else {
            return email;
        }
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
