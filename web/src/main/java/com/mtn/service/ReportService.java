package com.mtn.service;

import com.mtn.util.MtnLogger;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ReportService {

	private final SecurityService securityService;

	@Value("${report-generator.host}")
	private String reportGeneratorHost;

	public ReportService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Async
	public CompletableFuture<Boolean> buildZip(JSONObject json, String reportName) {
		Integer userId = this.securityService.getCurrentUser().getId();

		MtnLogger.info("Building Zip");
		try {
			this.createTableImage(reportName, json.get("sovData").toString(), "sov", userId);
			this.createTableImage(reportName, json.get("projections").toString(), "projections", userId);
			this.createTableImage(reportName, json.get("ratings").toString(), "ratings", userId);
			this.createTableImage(reportName, json.get("currentSummary").toString(), "currentSummary", userId);
			this.createTableImage(reportName, json.get("projectedSummary").toString(), "projectedSummary", userId);

			// create zip file from created files
			this.createZipFile(reportName, userId, json);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return CompletableFuture.completedFuture(true);
	}

	private void createZipFile(String reportName, Integer userId, JSONObject json) throws IOException {
		String[] tables = {"sov", "projections", "ratings", "currentSummary", "projectedSummary"};
		String reportFilePrefix = this.getTempFilePath() + "/" + userId + "-" + reportName;
		try (FileOutputStream fos = new FileOutputStream(reportFilePrefix + "~.zip")) {
			try (ZipOutputStream zos = new ZipOutputStream(fos)) {
				for (String tableName : tables) {
					zos.putNextEntry(new ZipEntry(tableName + ".png"));
					String fileName = reportFilePrefix + "-" + tableName + ".pdf";
					File pdf = new File(fileName);
					try (PDDocument document = PDDocument.load(pdf)) {
						PDFRenderer pdfRenderer = new PDFRenderer(document);
						BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
						ImageIOUtil.writeImage(bim, "png", zos, 300);
					}
					if (pdf.delete()) {
						MtnLogger.info(pdf.getName() + " was deleted");
					} else {
						MtnLogger.warn(pdf.getName() + " could not be deleted!");
					}
					zos.closeEntry();
				}

				this.addNarrativeTextFile(zos, json.getString("narrativeData"));
				this.addGoogleMapImage(zos, json.getString("mapUrl"));
				this.addCsvFile(zos, json, "marketShareData");
				this.addCsvFile(zos, json, "sovMapData");
			}
		}
		File completedZip = new File(reportFilePrefix + "~.zip");
		if (completedZip.renameTo(new File(reportFilePrefix + ".zip"))) {
			MtnLogger.info("Zip report completed");
		} else {
			MtnLogger.warn("Failed to finalize report zip!");
		}
	}

	private void createTableImage(String reportName, String jsonString, String table, Integer userId) throws IOException {
		URL url = new URL(this.reportGeneratorHost + "/pdf/" + table);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod("POST");

			// Send Json Body
			try (OutputStream os = con.getOutputStream()) {
				os.write(jsonString.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Try with resources ensures that document will be closed regardless of outcome
			try (PDDocument document = PDDocument.load(con.getInputStream())) {
				File tmpFile = new File(getTempFilePath() + "/" + userId + "-" + reportName + "-" + table + ".pdf");
				document.save(tmpFile);
				con.disconnect();
			}
		} finally {
			con.disconnect();
		}
	}

	private void addNarrativeTextFile(ZipOutputStream zos, String narrativeTxt) throws IOException {
		zos.putNextEntry(new ZipEntry("narrativeText.txt"));
		zos.write(narrativeTxt.getBytes());
		zos.closeEntry();
	}

	private void addGoogleMapImage(ZipOutputStream zos, String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Content-Type", MediaType.IMAGE_PNG_VALUE);

		BufferedImage bim = ImageIO.read(con.getInputStream());

		zos.putNextEntry(new ZipEntry("map.png"));
		ImageIOUtil.writeImage(bim, "png", zos, 300);
		zos.closeEntry();

		con.disconnect();
	}

	private void addCsvFile(ZipOutputStream zos, JSONObject json, String name) throws IOException {
		final StringBuilder sb = new StringBuilder();

		List<String> headers = json.getJSONArray(name + "Headers").toList().stream().map(String::valueOf).collect(Collectors.toList());
		sb.append(String.join(",", headers));
		sb.append("\r\n");

		JSONArray sectors = json.getJSONArray(name);
		for (int i = 0; i < sectors.length(); i++) {
			JSONObject sector = sectors.getJSONObject(i);
			String row = headers.stream()
					.map(sector::get)
					.map(String::valueOf)
					.collect(Collectors.joining(",", "", "\r\n"));
			sb.append(row);
		}

		zos.putNextEntry(new ZipEntry(name + ".csv"));
		zos.write(sb.toString().getBytes());
		zos.closeEntry();
	}

	public String getTempFilePath() throws IOException {
		//create a temp file
		File temp = File.createTempFile("temp-file-name", ".tmp");
		String absolutePath = temp.getAbsolutePath();
		return absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
	}

}
