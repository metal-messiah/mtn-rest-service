package com.mtn.controller;

import com.mtn.service.ReportService;
import com.mtn.service.SecurityService;
import com.mtn.util.MtnLogger;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	private final ReportService reportService;
	private final SecurityService securityService;

	@Autowired
	public ReportController(ReportService reportService, SecurityService securityService) {
		this.reportService = reportService;
		this.securityService = securityService;
	}

	@PostMapping(value = "zip")
	public ResponseEntity buildZip(HttpEntity<String> httpEntity, @RequestParam("report-name") String reportName) {
		try {
			JSONObject json = new JSONObject(httpEntity.getBody());
			this.reportService.buildZip(json, reportName);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(e.toString());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "zip", produces = "application/zip")
	public @ResponseBody byte[] getZip(@RequestParam("report-name") String reportName,
									   HttpServletResponse response) throws IOException {
		Integer userId = securityService.getCurrentUser().getId();

		try {
			Path reportDirPath = Paths.get(this.reportService.getTempFilePath() + "mtnReports" + File.separator + userId + "-" + reportName);
			File reportDir = new File(reportDirPath.toString());
			if (!reportDir.exists()) {
				response.setStatus(404);
				return null;
			}

			File errFile = new File(reportDirPath + File.separator + "err.txt");
			if (errFile.exists()) {
				response.setStatus(404);
				FileUtils.deleteDirectory(reportDir);
				return null;
			}

			File zipFile = new File(reportDirPath + File.separator + reportName + ".zip");

			if (zipFile.exists() && !zipFile.isDirectory()) {
				try (FileInputStream fis = new FileInputStream(zipFile)) {
					byte[] bytes = IOUtils.toByteArray(fis);
					fis.close();
					if (!zipFile.delete()) {
						MtnLogger.warn(zipFile.getName() + " could not be deleted");
					}
					if (!reportDir.delete()) {
						MtnLogger.warn("Failed to delete temp report directory for: " + reportName);
					}
					return bytes;
				}
			} else {
				response.setStatus(202);
				return null;
			}
		} catch(FileNotFoundException fe) {
			response.setStatus(404);
			return null;
		}
	}
}
