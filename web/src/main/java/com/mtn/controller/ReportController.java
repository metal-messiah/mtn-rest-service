package com.mtn.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	private void addSovImage(ZipOutputStream zos, String jsonString) throws IOException {
		URL url = new URL("http://localhost:3000/pdf/sov");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestMethod("POST");

		OutputStream os = con.getOutputStream();
		os.write(jsonString.getBytes(StandardCharsets.UTF_8));
		os.close();

		PDDocument document = PDDocument.load(con.getInputStream());
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);

		zos.putNextEntry(new ZipEntry("test.png"));
		ImageIOUtil.writeImage(bim, "png", zos, 300);
		zos.closeEntry();
		document.close();
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

	}

	@PostMapping(value="zip", produces = "application/zip")
	public void getZip(HttpEntity<String> httpEntity, HttpServletResponse response) {
		JSONObject json = new JSONObject(httpEntity.getBody());

		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			ZipOutputStream zos = new ZipOutputStream(bos);

			this.addSovImage(zos, json.get("sovData").toString());
			this.addNarrativeTextFile(zos, json.getString("narrativeData"));
			this.addGoogleMapImage(zos, json.getString("mapUrl"));

			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(500);
		}
	}

	@PostMapping(produces = "application/zip")
	public void handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			ZipOutputStream zos = new ZipOutputStream(bos);

			PDDocument document = PDDocument.load(file.getInputStream());
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);

			zos.putNextEntry(new ZipEntry("test.png"));
			ImageIOUtil.writeImage(bim, "png", zos, 300);
			zos.closeEntry();

			zos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
