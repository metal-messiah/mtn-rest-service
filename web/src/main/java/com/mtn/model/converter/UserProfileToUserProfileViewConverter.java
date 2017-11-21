package com.mtn.model.converter;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleUserProfileView;
import com.mtn.model.view.UserProfileView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/21/2017.
 */
public class UserProfileToUserProfileViewConverter implements Converter<UserProfile, UserProfileView> {

    @Override
    public UserProfileView convert(UserProfile userProfile) {
        return UserProfileToUserProfileViewConverter.build(userProfile);
    }

    public static UserProfileView build(UserProfile userProfile) {
        return new UserProfileView(userProfile);
    }
}
