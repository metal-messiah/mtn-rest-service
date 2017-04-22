package com.mtn.model.converter;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Collectors;

/**
 * Created by Allen on 4/21/2017.
 */
public class UserProfileToUserProfileViewConverter implements Converter<UserProfile, UserProfileView> {

    @Override
    public UserProfileView convert(UserProfile userProfile) {
        return UserProfileToUserProfileViewConverter.build(userProfile);
    }

    public static UserProfileView build(UserProfile userProfile) {
        UserProfileView viewModel = new UserProfileView();
        viewModel.setId(userProfile.getId());
        viewModel.setEmail(userProfile.getEmail());
        viewModel.setFirstName(userProfile.getFirstName());
        viewModel.setLastName(userProfile.getLastName());

        viewModel.setIdentities(userProfile.getIdentities().stream().map(UserIdentityToSimpleUserIdentityViewConverter::build).collect(Collectors.toList()));

        return viewModel;
    }
}
