package com.mtn.controller;

import com.mtn.model.domain.BannerSource;
import com.mtn.model.domain.BannerSourceSummary;
import com.mtn.model.view.BannerSourceView;
import com.mtn.service.BannerSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		Page<BannerSourceSummary> domainModels = ((BannerSourceService) this.entityService).findAllSummariesByQuery(queryMap, page);
		return ResponseEntity.ok(domainModels);
	}

	@PutMapping("/{bannerSourceId}/banner/{bannerId}")
	public ResponseEntity<BannerSourceView> assignBanner(@PathVariable("bannerSourceId") Integer bannerSourceId,
													 @PathVariable("bannerId") Integer bannerId) {
		BannerSource bannerSource = ((BannerSourceService) this.entityService).assignBanner(bannerSourceId, bannerId);
		return ResponseEntity.ok(new BannerSourceView(bannerSource));
	}

	@DeleteMapping("/{bannerSourceId}/banner")
	public ResponseEntity<BannerSourceView> unassignBanner(@PathVariable("bannerSourceId") Integer bannerSourceId) {
		BannerSource bannerSource = ((BannerSourceService) this.entityService).unassignBanner(bannerSourceId);
		return ResponseEntity.ok(new BannerSourceView(bannerSource));
	}

}
