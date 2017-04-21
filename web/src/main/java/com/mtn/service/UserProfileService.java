package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.repository.UserProfileRepository;
import com.mtn.util.QueryUtils;
import org.apache.commons.lang3.StringUtils;
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

    public Page<UserProfile> findAll(String q, Pageable page) {
        if (StringUtils.isNotBlank(q)) {
            return userProfileRepository.findAllByQueryString(QueryUtils.contains(q), page);
        } else {
            return userProfileRepository.findAll(page);
        }
    }
}
