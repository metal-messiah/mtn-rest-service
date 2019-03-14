package com.mtn.controller;

import com.mtn.model.domain.StoreList;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleUserProfileView;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.UserProfileService;
import com.mtn.service.StoreListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserProfileController extends CrudController<UserProfile, UserProfileView> {

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        super(userProfileService, UserProfileView::new);
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

    @PostMapping(path = "/{id}/subscribed-store-lists/{sId}")
    public ResponseEntity subscribeToStoreList(@PathVariable("id") Integer userId,
            @PathVariable("sId") Integer storeListId) {
        // StoreList storeList = UserProfileService.addOne(request);
        UserProfile userProfile = ((UserProfileService) this.entityService).subscribeToStoreListById(userId,
                storeListId);
        return ResponseEntity.ok(new SimpleUserProfileView(userProfile));
    }

    @DeleteMapping(path = "/{id}/subscribed-store-lists/{sId}")
    public ResponseEntity unsubscribeToStoreList(@PathVariable("id") Integer userId,
            @PathVariable("sId") Integer storeListId) {
        // StoreList storeList = UserProfileService.addOne(request);
        UserProfile userProfile = ((UserProfileService) this.entityService).unsubscribeToStoreListById(userId,
                storeListId);
        return ResponseEntity.ok(new SimpleUserProfileView(userProfile));
    }
}
