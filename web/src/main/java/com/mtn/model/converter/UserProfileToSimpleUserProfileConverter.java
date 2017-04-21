package com.mtn.model.converter;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleUserProfile;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/21/2017.
 */
public class UserProfileToSimpleUserProfileConverter implements Converter<UserProfile, SimpleUserProfile> {

    @Override
    public SimpleUserProfile convert(UserProfile userProfile) {
        SimpleUserProfile viewModel = new SimpleUserProfile();
        viewModel.setId(userProfile.getId());
        viewModel.setEmail(userProfile.getEmail());
        viewModel.setFirstName(userProfile.getFirstName());
        viewModel.setLastName(userProfile.getLastName());

        return viewModel;
    }
}
