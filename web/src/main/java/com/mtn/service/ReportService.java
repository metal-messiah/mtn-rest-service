package com.mtn.service;

import com.mtn.util.MtnLogger;
import org.apache.commons.io.FileUtils;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			Path reportDirPath = Paths.get(this.getTempFilePath() + "mtnReports" + File.separator + userId + "-" + reportName);

			// Clear out all previous attempts for this specific report
			FileUtils.deleteDirectory(new File(reportDirPath.toString()));

			// Create a fresh directory
			Path reportPath = Files.createDirectories(reportDirPath);

			// Build report
			try {
				Path pdfsPath = Files.createDirectories(Paths.get(reportPath + File.separator + "pdfs"));
				String[] tables = {"sovData", "projections", "ratings", "currentSummary", "projectedSummary"};
				for (String table : tables) {
					this.fetchTablePdf(json, table, pdfsPath);
				}

				// create zip file from created files
				this.createZipFile(reportName, reportPath, json, tables);
			} catch (IOException e) {
				File errorFile = new File(reportPath + File.separator + "err.txt");
				try (PrintWriter out = new PrintWriter(errorFile)) {
					e.printStackTrace(out);
				}
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return CompletableFuture.completedFuture(true);
	}

	private void createZipFile(String reportName, Path reportPath, JSONObject json, String[] tables) throws IOException {
		File zipFile = new File(reportPath + File.separator + "~.zip");
		try (FileOutputStream fos = new FileOutputStream(zipFile)) {
			try (ZipOutputStream zos = new ZipOutputStream(fos)) {
				// Convert all PDFs to images, add Images to ZIP, delete PDFs
				for (String tableName : tables) {
					this.addImageToZip(reportPath, tableName, zos);
				}

				// Add other files to the ZIP
				this.addNarrativeTextFile(zos, json.getString("narrativeData"));
				this.addGoogleMapImage(zos, json.getString("mapUrl"));
				this.addCsvFile(zos, json, "marketShareData");
				this.addCsvFile(zos, json, "sovMapData");
			}
		}

		// Rename temp zip to finalized zip
		if (!zipFile.renameTo(new File(reportPath + File.separator  + reportName + ".zip"))) {
			MtnLogger.warn("Failed to finalize report zip!");
		}
		if (!new File(reportPath + File.separator + "pdfs").delete()) {
			MtnLogger.info("Failed to delete pdfs dir for report: " + reportName);
		}
	}

	private void addImageToZip(Path reportPath, String tableName, ZipOutputStream zos) throws IOException {
		// Get PDF file
		String fileName = reportPath + File.separator + "pdfs" + File.separator + tableName + ".pdf";
		File pdf = new File(fileName);

		try (PDDocument document = PDDocument.load(pdf)) {
			// Create Image from PDF
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);

			// Write to zip file
			try {
				zos.putNextEntry(new ZipEntry(tableName + ".png"));
				ImageIOUtil.writeImage(bim, "png", zos, 300);
			} finally {
				zos.closeEntry();
			}
		}

		// Delete the PDF file
		if (!pdf.delete()) {
			MtnLogger.warn("Failed to delete pdf: " + pdf.getName());
		}
	}

	private void fetchTablePdf(JSONObject json, String table, Path reportPath) throws IOException {
		URL url = new URL(this.reportGeneratorHost + "/pdf/" + table);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod("POST");

			// Send Json Body
			try (OutputStream os = con.getOutputStream()) {
				os.write(json.get(table).toString().getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Try with resources ensures that document will be closed regardless of outcome
			try (PDDocument document = PDDocument.load(con.getInputStream())) {
				File tmpFile = new File(reportPath + File.separator + table + ".pdf");
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
					.map(str -> str.equals("null") ? "" : str)
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
		return absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)) + File.separator;
	}

}
