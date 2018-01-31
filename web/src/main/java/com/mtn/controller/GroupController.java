package com.mtn.controller;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.model.simpleView.SimpleGroupView;
import com.mtn.model.simpleView.SimpleUserProfileView;
import com.mtn.model.view.GroupView;
import com.mtn.service.GroupService;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/group")
public class GroupController extends CrudControllerImpl<Group> {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.POST)
    public ResponseEntity addOneMemberToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
        Group domainModel = getEntityService().addOneMemberToGroup(groupId, userId);
        return ResponseEntity.ok(getViewFromModel(domainModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllByDisplayName(@RequestParam(value = "displayName", required = false) String displayName, Pageable page) {
        Page<Group> domainModels;
        if (StringUtils.isNotBlank(displayName)) {
            domainModels = getEntityService().findAllByNameUsingSpecs(displayName, page);
        } else {
            domainModels = getEntityService().findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @RequestMapping(value = "/{id}/member", method = RequestMethod.GET)
    public ResponseEntity findAllMembersForGroup(@PathVariable("id") Integer groupId) {
        List<UserProfile> domainModels = userProfileService.findAllByGroupIdUsingSpecs(groupId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleUserProfileView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOneMemberFromGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
        Group domainModel = this.getEntityService().removeOneMemberFromGroup(groupId, userId);
        return ResponseEntity.ok(this.getViewFromModel(domainModel));
    }

    @Override
    public GroupService getEntityService() {
        return groupService;
    }

    @Override
    public Object getViewFromModel(Group model) {
        return new GroupView(model);
    }

    @Override
    public Object getSimpleViewFromModel(Group model) {
        return new SimpleGroupView(model);
    }
}
