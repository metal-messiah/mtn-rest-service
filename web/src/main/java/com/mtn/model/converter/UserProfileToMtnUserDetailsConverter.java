package com.mtn.model.converter;

import com.mtn.model.MtnUserDetails;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Permission;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by Allen on 5/10/2017.
 */
public class UserProfileToMtnUserDetailsConverter implements Converter<UserProfile, MtnUserDetails> {

    @Override
    public MtnUserDetails convert(UserProfile userProfile) {
        MtnUserDetails mtnUserDetails = null;

        if (userProfile != null) {
            mtnUserDetails = new MtnUserDetails();
            mtnUserDetails.setEmail(userProfile.getEmail());
            mtnUserDetails.setFirstName(userProfile.getFirstName());
            mtnUserDetails.setLastName(userProfile.getLastName());
            mtnUserDetails.setIsActive(userProfile.getDeletedDate() == null);

            if (userProfile.getRole() != null) {
                for (Permission permission : userProfile.getRole().getPermissions()) {
                    mtnUserDetails.getAuthorities().add(new SimpleGrantedAuthority(permission.getSystemName().toString()));
                }
            }
        }

        return mtnUserDetails;
    }
}
