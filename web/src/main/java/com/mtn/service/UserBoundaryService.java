package com.mtn.service;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.repository.UserBoundaryRepository;
import com.mtn.validators.UserBoundaryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBoundaryService extends EntityService<UserBoundary, UserBoundaryView> {

    @Autowired
    public UserBoundaryService(SecurityService securityService,
                               UserBoundaryRepository repository,
							   UserBoundaryValidator validator) {
        super(securityService, repository, validator, UserBoundary::new);
    }

    public List<UserBoundary> getUserBoundaries(Integer userId) {
        return ((UserBoundaryRepository) this.repository).findUserBoundaries(userId);
    }

    @Override
    protected void setEntityAttributesFromRequest(UserBoundary object, UserBoundaryView request) {
        object.setBoundaryName(request.getBoundaryName());
    }

    @Override
    public void handleAssociationsOnDeletion(UserBoundary existing) {
        // TODO delete boundary by orphan (if no related user OR project)
    }
}
