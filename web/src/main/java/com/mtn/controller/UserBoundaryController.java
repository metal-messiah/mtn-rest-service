package com.mtn.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.simpleView.SimpleUserBoundaryView;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.service.SecurityService;
import com.mtn.service.UserBoundaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-boundary")
public class UserBoundaryController extends CrudController<UserBoundary, UserBoundaryView> {

    private final SecurityService securityService;

    @Autowired
    public UserBoundaryController(UserBoundaryService entityService, SecurityService securityService) {
        super(entityService, UserBoundaryView::new);
        this.securityService = securityService;
    }

    @GetMapping
    public ResponseEntity<List<SimpleUserBoundaryView>> findRecordsByUserId() {
        Integer userId = securityService.getCurrentUser().getId();

        List<UserBoundary> domainModels = ((UserBoundaryService) this.entityService).findRecordsByUserId(userId);
        List<SimpleUserBoundaryView> views = domainModels.stream().map(SimpleUserBoundaryView::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(views);
    }

}
