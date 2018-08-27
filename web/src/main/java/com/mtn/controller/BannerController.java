package com.mtn.controller;

import com.mtn.model.domain.Banner;
import com.mtn.model.simpleView.SimpleBannerView;
import com.mtn.model.view.BannerView;
import com.mtn.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banner")
public class BannerController extends CrudController<Banner, BannerView> {

    @Autowired
    public BannerController(BannerService entityService) {
        super(entityService, BannerView::new);
    }

    @GetMapping
    public ResponseEntity findAll(Pageable page, @RequestParam(value = "query", required = false) String query) {
        Page<Banner> domainModels;
        if (query != null) {
            domainModels = ((BannerService) this.entityService).findAllByQueryUsingSpecs(page, query);
        } else {
            domainModels = this.entityService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(SimpleBannerView::new));
    }

}
