package com.mtn.service;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.UserBoundary;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.repository.UserBoundaryRepository;
import com.mtn.validators.UserBoundaryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBoundaryService extends EntityService<UserBoundary, UserBoundaryView> {

    UserProfileService userProfileService;
    BoundaryService boundaryService;

    @Autowired
    public UserBoundaryService(SecurityService securityService, UserBoundaryRepository repository,
            UserBoundaryValidator validator, UserProfileService userProfileService, BoundaryService boundaryService) {
        super(securityService, repository, validator, UserBoundary::new);
        this.userProfileService = userProfileService;
        this.boundaryService = boundaryService;
    }

    public List<UserBoundary> getUserBoundaries(Integer userId) {
        return ((UserBoundaryRepository) this.repository).findUserBoundaries(userId);
    }

    @Override
    protected void setEntityAttributesFromRequest(UserBoundary object, UserBoundaryView request) {
        UserProfile user = this.userProfileService.findOne(request.getUser().getId());
        Boundary boundary = this.boundaryService.findOne(request.getBoundary().getId());
        object.setBoundaryName(request.getBoundaryName());
        object.setBoundary(boundary);
        object.setUser(user);
    }

    @Override
    public void handleAssociationsOnDeletion(UserBoundary existing) {
        // TODO delete boundary by orphan (if no related user OR project)
    }
}
