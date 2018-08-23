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

@RestController
@RequestMapping("/api/group")
public class GroupController extends CrudController<Group, GroupView> {

    private final UserProfileService userProfileService;

    @Autowired
    public GroupController(GroupService groupService, UserProfileService userProfileService) {
        super(groupService, GroupView::new);
        this.userProfileService = userProfileService;
    }

    @RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.POST)
    public ResponseEntity addOneMemberToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
        Group domainModel = ((GroupService) this.entityService).addOneMemberToGroup(groupId, userId);
        return ResponseEntity.ok(new GroupView(domainModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllByDisplayName(@RequestParam(value = "displayName", required = false) String displayName, Pageable page) {
        Page<Group> domainModels;
        if (StringUtils.isNotBlank(displayName)) {
            domainModels = ((GroupService) this.entityService).findAllByNameUsingSpecs(displayName, page);
        } else {
            domainModels = this.entityService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(SimpleGroupView::new));
    }

    @RequestMapping(value = "/{id}/member", method = RequestMethod.GET)
    public ResponseEntity findAllMembersForGroup(@PathVariable("id") Integer groupId) {
        List<UserProfile> domainModels = userProfileService.findAllByGroupIdUsingSpecs(groupId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleUserProfileView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOneMemberFromGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
        Group domainModel = ((GroupService) this.entityService).removeOneMemberFromGroup(groupId, userId);
        return ResponseEntity.ok(new GroupView(domainModel));
    }

}
