package com.mtn.model.converter;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleUserProfileView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/21/2017.
 */
public class UserProfileToSimpleUserProfileViewConverter implements Converter<UserProfile, SimpleUserProfileView> {

    @Override
    public SimpleUserProfileView convert(UserProfile userProfile) {
        return UserProfileToSimpleUserProfileViewConverter.build(userProfile);
    }

    public static SimpleUserProfileView build(UserProfile userProfile) {
        return new SimpleUserProfileView(userProfile);
    }
}
