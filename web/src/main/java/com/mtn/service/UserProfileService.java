package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 4/21/2017.
 */
@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }
}
