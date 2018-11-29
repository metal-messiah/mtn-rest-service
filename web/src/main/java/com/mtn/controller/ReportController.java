package com.mtn.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	private void getSovImage(ZipOutputStream zos, String jsonString) throws IOException {
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

	@PostMapping(value="zip", produces = "application/zip")
	public void getZip(HttpEntity<String> httpEntity, HttpServletResponse response) {
		JSONObject json = new JSONObject(httpEntity.getBody());

		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			ZipOutputStream zos = new ZipOutputStream(bos);

			this.getSovImage(zos, json.get("sovData").toString());

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
