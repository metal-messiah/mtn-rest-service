package com.mtn.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleUserProfileView;
import com.mtn.model.simpleView.SimpleBoundaryView;
import com.mtn.model.view.BoundaryView;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.BoundaryService;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserProfileController extends CrudController<UserProfile, UserProfileView> {

    private final BoundaryService boundaryService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, BoundaryService boundaryService) {
        super(userProfileService, UserProfileView::new);
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
}
