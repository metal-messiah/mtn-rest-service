package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 4/21/2017.
 */
@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Page<UserProfile> findAll(Pageable page) {
        return userProfileRepository.findAll(page);
    }

    public UserProfile findOne(Integer id) {
        return userProfileRepository.findOne(id);
    }

    public UserProfile findOne(String providerUserId) {
        return userProfileRepository.findOneByProviderUserId(providerUserId);
    }

    public Page<UserProfile> query(String q, Pageable page) {
        return userProfileRepository.findAllByQueryString(q, page);
    }

}
