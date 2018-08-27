package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.model.view.GroupView;
import com.mtn.model.view.UserProfileView;
import com.mtn.repository.GroupRepository;
import com.mtn.repository.specification.GroupSpecifications;
import com.mtn.validators.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class GroupService extends EntityService<Group, GroupView> {

    private final UserProfileService userProfileService;

    @Autowired
    public GroupService(SecurityService securityService,
                        GroupRepository repository,
                        GroupValidator validator, UserProfileService userProfileService) {
        super(securityService, repository, validator, Group::new);
        this.userProfileService = userProfileService;
    }

    public Page<Group> findAllByNameUsingSpecs(String name, Pageable page) {
        return this.repository.findAll(
                where(GroupSpecifications.displayNameContains(name))
                        .and(GroupSpecifications.isNotDeleted())
                , page
        );
    }

    @Transactional
    public Group removeOneMemberFromGroup(Integer groupId, Integer userId) {
        Group group = findOneUsingSpecs(groupId);

        group.getMembers().removeIf(member -> member.getId().equals(userId));

        return group;
    }

    @Transactional
    public Group addOneMemberToGroup(Integer groupId, Integer userId) {
        Group group = findOneUsingSpecs(groupId);

        UserProfile member = userProfileService.findOneUsingSpecs(userId);
        group.getMembers().add(member);

        return group;
    }

    @Override
    protected void setEntityAttributesFromRequest(Group entity, GroupView request) {
        entity.setDisplayName(request.getDisplayName());
        entity.setDescription(request.getDescription());
    }

    @Override
    public void handleAssociationsOnDeletion(Group existing) {
        existing.getMembers().forEach(member -> {
            member.setGroup(null);
            this.userProfileService.updateOne(new UserProfileView(member));
        });
    }

}
