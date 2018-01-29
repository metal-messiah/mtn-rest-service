package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.repository.GroupRepository;
import com.mtn.validators.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mtn.repository.specification.GroupSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class GroupServiceImpl extends EntityServiceImpl<Group> implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupValidator groupValidator;

    @Override
    @Transactional
    public Group addOneMemberToGroup(Integer groupId, Integer userId) {
        Group group = findOneUsingSpecs(groupId);
        getValidator().validateNotNull(group);

        UserProfile member = userProfileService.findOneUsingSpecs(userId);
        userProfileService.getValidator().validateNotNull(member);

        group.getMembers().add(member);
        group.setUpdatedBy(securityService.getCurrentUser());

        return group;
    }

    @Override
    public Group getUpdatedEntity(Group existing, Group request) {
        existing.setDisplayName(request.getDisplayName());
        existing.setDescription(request.getDescription());

        updateMembers(existing, request);

        return existing;
    }

    @Override
    public void handleAssociationsOnCreation(Group request) {
        Set<UserProfile> requestedMembers = request.getMembers();
        requestedMembers.forEach(member -> member.setGroup(request));
    }

    @Override
    public void handleAssociationsOnDeletion(Group existing) {
        existing.setMembers(new HashSet<>());
    }

    @Override
    public Group findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<Group> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotDeleted())
                , page
        );
    }

    @Override
    public Page<Group> findAllByNameUsingSpecs(String name, Pageable page) {
        return getEntityRepository().findAll(
                where(displayNameContains(name))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public Group findOneByDisplayName(String displayName) {
        return getEntityRepository().findOneByDisplayName(displayName);
    }

    @Override
    @Transactional
    public Group removeOneMemberFromGroup(Integer groupId, Integer userId) {
        Group group = findOneUsingSpecs(groupId);
        getValidator().validateNotNull(group);

        group.getMembers().removeIf(member -> member.getId().equals(userId));
        group.setUpdatedBy(securityService.getCurrentUser());

        return group;
    }

    private void updateMembers(Group existing, Group request) {
        //Remove members
        List<UserProfile> removedMembers = new ArrayList<>();
        for (UserProfile member : existing.getMembers()) {
            if (!request.getMembers().contains(member)) {
                member.setGroup(null);
                removedMembers.add(member);
            }
        }
        existing.getMembers().removeAll(removedMembers);

        //Add members
        for (UserProfile member : request.getMembers()) {
            if (!existing.getMembers().contains(member)) {
                UserProfile existingMember = userProfileService.findOneUsingSpecs(member.getId());
                if (existingMember != null) {
                    existing.getMembers().add(existingMember);
                    existingMember.setGroup(existing);
                }
            }
        }
    }

    @Override
    public String getEntityName() {
        return "Group";
    }

    @Override
    public GroupRepository getEntityRepository() {
        return groupRepository;
    }

    @Override
    public GroupValidator getValidator() {
        return groupValidator;
    }
}
