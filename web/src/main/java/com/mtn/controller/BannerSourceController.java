package com.mtn.controller;

import com.mtn.model.domain.BannerSource;
import com.mtn.model.simpleView.SimpleBannerSourceView;
import com.mtn.model.view.BannerSourceView;
import com.mtn.service.BannerSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/banner-source")
public class BannerSourceController extends CrudController<BannerSource, BannerSourceView> {

	@Autowired
	public BannerSourceController(BannerSourceService bannerSourceService) {
		super(bannerSourceService, BannerSourceView::new);
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page, @RequestParam Map<String, String> queryMap) {
		Page<BannerSource> domainModels = ((BannerSourceService) this.entityService).findAllByQuery(queryMap, page);
		return ResponseEntity.ok(domainModels.map(SimpleBannerSourceView::new));
	}

}
