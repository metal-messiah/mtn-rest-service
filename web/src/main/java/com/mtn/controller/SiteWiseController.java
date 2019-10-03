package com.mtn.controller;

import com.mtn.service.SiteWiseService;
import com.mtn.util.MtnLogger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	public void downloadActiveAndFuture(HttpServletResponse response) {
		try {
			File file = siteWiseService.getActiveAndFutureStoresFile();
			respondWithFile(file, "MTN_Locations", response);
		} catch (IOException e) {
			MtnLogger.error("Failed", e);
			response.setStatus(500);
		}
	}

	@GetMapping("empty-sites")
	public void downloadEmptySites(HttpServletResponse response) {
		try {
			File file = siteWiseService.getEmptySitesFile();
			respondWithFile(file, "Empty_Sites", response);
		} catch (IOException e) {
			MtnLogger.error("Failed", e);
			response.setStatus(500);
		}
	}

	private void respondWithFile(File file, String fileName, HttpServletResponse response) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String date = formatter.format(LocalDateTime.now().toLocalDate());

		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; filename=" + date + "_" + fileName + ".csv");

		try (OutputStream out = response.getOutputStream();
			 FileInputStream in = new FileInputStream(file)) {
			// copy from in to out
			IOUtils.copy(in, out);
		} catch (IOException e) {
			response.setStatus(500);
		}

		if (file.delete()) {
			MtnLogger.info("Successfully deleted temp file.");
		} else {
			MtnLogger.warn("Failed to delete temp file Empty Sites");
		}
	}

}
