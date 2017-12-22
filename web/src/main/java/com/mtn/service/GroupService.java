package com.mtn.service;

import com.mtn.exception.DeletedEntityReactivationException;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.repository.GroupRepository;
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
public class GroupService extends ValidatingDataService<Group> {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public Group addOne(Group request) {
        try {
            validateForInsert(request);
        } catch (DeletedEntityReactivationException e) {
            return reactivateOne((Group) e.getEntity(), request);
        }

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        Set<UserProfile> requestedMembers = request.getMembers();
        requestedMembers.forEach(member -> member.setGroup(request));

        return groupRepository.save(request);
    }

    @Transactional
    public Group addOneMemberToGroup(Integer groupId, Integer userId) {
        Group group = findOneUsingSpecs(groupId);
        validateNotNull(group);

        UserProfile member = userProfileService.findOneUsingSpecs(userId);
        userProfileService.validateNotNull(member);

        group.getMembers().add(member);
        group.setUpdatedBy(securityService.getCurrentUser());

        return group;
    }

    @Transactional
    public void deleteOne(Integer id) {
        Group existing = findOneUsingSpecs(id);
        validateNotNull(existing);

        existing.setMembers(new HashSet<>());

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public Page<Group> findAllUsingSpecs(Pageable page) {
        return groupRepository.findAll(
                where(isNotDeleted())
                , page
        );
    }

    public Page<Group> findAllByNameUsingSpecs(String name, Pageable page) {
        return groupRepository.findAll(
                where(displayNameContains(name))
                        .and(isNotDeleted())
                , page
        );
    }

    public Group findOne(Integer id) {
        return groupRepository.findOne(id);
    }

    public Group findOneUsingSpecs(Integer id) {
        return groupRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    public Group findOneByDisplayName(String displayName) {
        return groupRepository.findOneByDisplayName(displayName);
    }

    @Transactional
    public Group reactivateOne(Group existingGroup, Group request) {
        existingGroup.setDeletedBy(null);
        existingGroup.setDeletedDate(null);

        return updateOne(existingGroup, request);
    }

    @Transactional
    public Group removeOneMemberFromGroup(Integer groupId, Integer userId) {
        Group group = findOneUsingSpecs(groupId);
        validateNotNull(group);

        group.getMembers().removeIf(member -> member.getId().equals(userId));
        group.setUpdatedBy(securityService.getCurrentUser());

        return group;
    }

    @Transactional
    public Group updateOne(Integer id, Group request) {
        validateNotNull(request);
        validateForUpdate(request);

        Group existing = findOneUsingSpecs(id);
        validateNotNull(existing);

        return updateOne(existing, request);
    }

    @Transactional
    public Group updateOne(Group existing, Group request) {
        existing.setDisplayName(request.getDisplayName());
        existing.setDescription(request.getDescription());
        existing.setUpdatedBy(securityService.getCurrentUser());

        updateMembers(existing, request);

        return existing;
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
    public void validateBusinessRules(Group object) {
        //No special rules to enforce yet
    }

    @Override
    public void validateDoesNotExist(Group object) {
        Group existing = findOneByDisplayName(object.getDisplayName());
        if (existing != null) {
            if (existing.getDeletedDate() != null) {
                throw new DeletedEntityReactivationException(existing);
            } else {
                throw new IllegalArgumentException("Group with this displayName already exists");
            }
        }
    }
}
