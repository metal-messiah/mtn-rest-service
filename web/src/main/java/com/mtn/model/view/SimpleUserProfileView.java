package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Allen on 4/21/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserProfileView {

    protected Integer id;
    protected String email;
    protected String firstName;
    protected String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
