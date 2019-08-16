package com.mtn.controller;

import com.mtn.service.SiteWiseService;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/site-wise")
public class SiteWiseController {

	private final SiteWiseService siteWiseService;


	@Autowired
	public SiteWiseController(SiteWiseService siteWiseService) {
		this.siteWiseService = siteWiseService;
	}

	@GetMapping()
	public ResponseEntity submitFile() {
		this.siteWiseService.buildAndTransmitActiveAndFutureStoreData();
		return ResponseEntity.ok("File Submitted");
	}

	@GetMapping("active-and-future")
	public ResponseEntity downloadActiveAndFuture() {
		try {
			return getDownloadableResponseEntity(siteWiseService.getActiveAndFutureStoresFile(), "MTN_Locations");
		} catch (IOException e) {
			MtnLogger.warn("Failed", e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("empty-sites")
	public ResponseEntity downloadEmptySites() {
		try {
			return this.getDownloadableResponseEntity(siteWiseService.getEmptySitesFile(), "Empty_Sites");
		} catch (IOException e) {
			MtnLogger.warn("Failed", e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	private ResponseEntity getDownloadableResponseEntity(File file, String fileName) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String date = formatter.format(LocalDateTime.now().toLocalDate());
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=" + date + "_" + fileName + ".csv")
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(new FileSystemResource(file));
	}

}
