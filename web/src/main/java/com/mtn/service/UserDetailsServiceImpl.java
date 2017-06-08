package com.mtn.service;

import com.mtn.model.MtnUserDetails;
import com.mtn.model.converter.UserProfileToMtnUserDetailsConverter;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private PermissionService permissionService;

    private UserProfileToMtnUserDetailsConverter converter = new UserProfileToMtnUserDetailsConverter();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileService.findOneByEmail(email);
        MtnUserDetails userDetails = converter.convert(userProfile);

        //If system administrator, add all permissions
        if (email.equals("system.administrator@mtnra.com")) {
            for (Permission permission : permissionService.findAll(null)) {
                userDetails.getAuthorities().add(new SimpleGrantedAuthority(permission.getSystemName()));
            }
        }

        return userDetails;
    }
}
