package com.mtn.controller;

import com.mtn.service.ReportService;
import com.mtn.service.SecurityService;
import com.mtn.util.MtnLogger;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
	public ResponseEntity getZip(HttpEntity<String> httpEntity, @RequestParam("report-name") String reportName) {
		try {
			JSONObject json = new JSONObject(httpEntity.getBody());

			this.reportService.buildZip(json, reportName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "zip", produces = "application/zip")
	public @ResponseBody
	byte[] getZip(@RequestParam("report-name") String reportName, HttpServletResponse response) throws IOException {
		Integer userId = securityService.getCurrentUser().getId();

		try {
			String tmpPath = this.reportService.getTempFilePath();
			File zipFile = new File(tmpPath + "/" + userId + "-" + reportName + ".zip");
			try (FileInputStream fis = new FileInputStream(zipFile)) {
				byte[] bytes = IOUtils.toByteArray(fis);
				fis.close();
				if (zipFile.delete()) {
					MtnLogger.info(zipFile.getName() + " was successfully deleted");
				} else {
					MtnLogger.warn(zipFile.getName() + " could not be deleted");
				}
				return bytes;
			}
		} catch(FileNotFoundException fe) {
			response.setStatus(404);
			return null;
		}
	}

//	@PostMapping(produces = "application/zip")
//	public void handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
//		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
//
//		try {
//			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
//			ZipOutputStream zos = new ZipOutputStream(bos);
//
//			PDDocument document = PDDocument.load(file.getInputStream());
//			PDFRenderer pdfRenderer = new PDFRenderer(document);
//			BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
//
//			zos.putNextEntry(new ZipEntry("test.png"));
//			ImageIOUtil.writeImage(bim, "png", zos, 300);
//			zos.closeEntry();
//
//			zos.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
