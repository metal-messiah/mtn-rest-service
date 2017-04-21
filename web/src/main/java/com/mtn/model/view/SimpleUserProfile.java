package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/21/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserProfile {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

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

    public static SimpleUserProfile build(UserProfile domainModel) {
        SimpleUserProfile viewModel = new SimpleUserProfile();
        viewModel.setId(domainModel.getId());
        viewModel.setEmail(domainModel.getEmail());
        viewModel.setFirstName(domainModel.getFirstName());
        viewModel.setLastName(domainModel.getLastName());

        return viewModel;
    }

    public static List<SimpleUserProfile> build(List<UserProfile> domainModels) {
        return domainModels.stream().map(SimpleUserProfile::build).collect(Collectors.toList());
    }
}
