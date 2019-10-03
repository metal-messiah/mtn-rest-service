package com.mtn.controller;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Group;
import com.mtn.model.domain.Role;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleUserProfileView;
import com.mtn.model.view.BoundaryView;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.BoundaryService;
import com.mtn.service.GroupService;
import com.mtn.service.RoleService;
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
@RequestMapping("/api/user")
public class UserProfileController extends CrudController<UserProfile, UserProfileView> {

	private final RoleService roleService;
	private final GroupService groupService;
	private final BoundaryService boundaryService;

	@Autowired
	public UserProfileController(UserProfileService userProfileService,
								 RoleService roleService,
								 GroupService groupService,
								 BoundaryService boundaryService) {
		super(userProfileService, UserProfileView::new);
		this.roleService = roleService;
		this.groupService = groupService;
		this.boundaryService = boundaryService;

	}

	@GetMapping
	public ResponseEntity findAll(@RequestParam(value = "q", required = false) String q, Pageable page) {
		Page<UserProfile> domainModels;
		if (StringUtils.isNotBlank(q)) {
			domainModels = ((UserProfileService) this.entityService).query(q, page);
		} else {
			domainModels = this.entityService.findAllUsingSpecs(page);
		}
		return ResponseEntity.ok(domainModels.map(UserProfileView::new));
	}

	@GetMapping("/{userProfileId}/boundary")
	public ResponseEntity<List<BoundaryView>> getUserPermissions(@PathVariable Integer userProfileId) {
		List<Boundary> userBoundaries = this.boundaryService.getUserBoundaries(userProfileId);
		return ResponseEntity.ok(userBoundaries.stream().map(BoundaryView::new).collect(Collectors.toList()));
	}

	@PutMapping(path = "/{userID}/boundary/{boundaryID}")
	public ResponseEntity associateBoundaryToUser(@PathVariable("userID") Integer userID,
												  @PathVariable("boundaryID") Integer boundaryID) {
		Boundary boundary = boundaryService.findOne(boundaryID);

		UserProfile userProfile = ((UserProfileService) this.entityService).setUserBoundary(userID, boundary);
		return ResponseEntity.ok(new SimpleUserProfileView(userProfile));
	}

	@PutMapping("{userId}/role/{roleId}")
	public ResponseEntity setUserRole(@PathVariable() Integer userId, @PathVariable() Integer roleId) {
		Role role = this.roleService.findOneUsingSpecs(roleId);
		UserProfile userProfile = ((UserProfileService) this.entityService).setUserRole(userId, role);
		return ResponseEntity.ok(new UserProfileView(userProfile));
	}

	@DeleteMapping("{userId}/role")
	public ResponseEntity deleteUserRole(@PathVariable() Integer userId) {
		UserProfile userProfile = ((UserProfileService) this.entityService).setUserRole(userId, null);
		return ResponseEntity.ok(new UserProfileView(userProfile));
	}

	@PutMapping("{userId}/group/{groupId}")
	public ResponseEntity setUserGroup(@PathVariable() Integer userId, @PathVariable() Integer groupId) {
		Group group = this.groupService.findOneUsingSpecs(groupId);
		UserProfile userProfile = ((UserProfileService) this.entityService).setUserGroup(userId, group);
		return ResponseEntity.ok(new UserProfileView(userProfile));
	}

	@DeleteMapping("{userId}/group")
	public ResponseEntity deleteUserGroup(@PathVariable() Integer userId) {
		UserProfile userProfile = ((UserProfileService) this.entityService).setUserGroup(userId, null);
		return ResponseEntity.ok(new UserProfileView(userProfile));
	}

	@PostMapping(path = "/{id}/subscribed-store-lists/{sId}")
	public ResponseEntity subscribeToStoreList(@PathVariable("id") Integer userId,
											   @PathVariable("sId") Integer storeListId) {
		UserProfile userProfile = ((UserProfileService) this.entityService).subscribeToStoreListById(userId,
				storeListId);
		return ResponseEntity.ok(new SimpleUserProfileView(userProfile));
	}

	@DeleteMapping(path = "/{id}/subscribed-store-lists/{sId}")
	public ResponseEntity unsubscribeToStoreList(@PathVariable("id") Integer userId,
												 @PathVariable("sId") Integer storeListId) {
		UserProfile userProfile = ((UserProfileService) this.entityService).unsubscribeToStoreListById(userId,
				storeListId);
		return ResponseEntity.ok(new SimpleUserProfileView(userProfile));
	}

	@PutMapping("/{userProfileId}/permissions")
	public ResponseEntity<UserProfileView> updatePermissions(@PathVariable Integer userProfileId,
															 @RequestBody List<Integer> permissionIds) {
		UserProfile userProfile = ((UserProfileService) this.entityService).updatePermissions(userProfileId, permissionIds);
		return ResponseEntity.ok(new UserProfileView(userProfile));
	}

}
