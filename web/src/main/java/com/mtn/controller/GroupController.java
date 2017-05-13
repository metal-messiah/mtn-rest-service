package com.mtn.controller;

import com.mtn.model.converter.GroupToSimpleGroupViewConverter;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Group;
import com.mtn.model.view.SimpleUserProfileView;
import com.mtn.model.view.auth.GroupView;
import com.mtn.service.GroupService;
import com.mtn.service.SecurityService;
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
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody Group request) {
        securityService.checkPermission("GROUPS_CREATE");

        Group domainModel = groupService.addOne(request);
        return ResponseEntity.ok(new GroupView(domainModel));
    }

    @RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.POST)
    public ResponseEntity addOneMemberToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
        securityService.checkPermission("GROUPS_UPDATE");

        Group domainModel = groupService.addOneMemberToGroup(groupId, userId);
        return ResponseEntity.ok(new GroupView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("GROUPS_DELETE");

        groupService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "displayName", required = false) String displayName, Pageable page) {
        securityService.checkPermission("GROUPS_READ");

        Page<Group> domainModels;
        if (StringUtils.isNotBlank(displayName)) {
            domainModels = groupService.findAllByNameUsingSpecs(displayName, page);
        } else {
            domainModels = groupService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(new GroupToSimpleGroupViewConverter()));
    }

    @RequestMapping(value = "/{id}/member", method = RequestMethod.GET)
    public ResponseEntity findAllMembersForGroup(@PathVariable("id") Integer groupId) {
        securityService.checkPermission("GROUPS_READ");

        List<UserProfile> domainModels = userProfileService.findAllByGroupIdUsingSpecs(groupId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleUserProfileView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("GROUPS_READ");

        Group domainModel = groupService.findOneUsingSpecs(id);
        return ResponseEntity.ok(new GroupView(domainModel));
    }

    @RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOneMemberFromGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
        securityService.checkPermission("GROUPS_UPDATE");

        Group domainModel = groupService.removeOneMemberFromGroup(groupId, userId);
        return ResponseEntity.ok(new GroupView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody Group request) {
        securityService.checkPermission("GROUPS_UPDATE");

        Group domainModel = groupService.updateOne(id, request);
        return ResponseEntity.ok(new GroupView(domainModel));
    }
}
