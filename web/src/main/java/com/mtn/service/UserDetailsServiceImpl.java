package com.mtn.service;

import com.mtn.model.converter.UserProfileToMtnUserDetailsConverter;
import com.mtn.model.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 5/10/2017.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserProfileService userProfileService;

    private UserProfileToMtnUserDetailsConverter converter = new UserProfileToMtnUserDetailsConverter();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileService.findOneByEmail(email);
        return converter.convert(userProfile);
    }
}
