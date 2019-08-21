package com.mtn.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.view.StoreVolumeView;
import com.mtn.util.MtnLogger;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class CommissaryService {

	private final StoreVolumeService volumeService;
	private final StoreSourceService storeSourceService;

	@Autowired
	public CommissaryService(StoreVolumeService volumeService,
							 StoreSourceService storeSourceService) {
		this.volumeService = volumeService;
		this.storeSourceService = storeSourceService;
	}

	public void scrapeCommissaryData() {
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		String uri = "https://www.commissaries.com/our-agency/FOIA/FOIA-Electronic-Reading-Room";
		try {
			HtmlPage page = client.getPage(uri);
			// Get anchor elements with "Cumulative Sales" in the text content
			List<HtmlElement> items = page.getByXPath("//a[contains(text(),'Cumulative Sales')]");
			Integer year = LocalDate.now().getYear();
			Optional<HtmlElement> currYear = items.stream().filter(i -> i.getTextContent().contains(String.valueOf(year))).findFirst();
			if (currYear.isPresent()) {
				String fileUri = currYear.get().getAttribute("href").replace("http:", "https:");
				File commissaryFile = getCommissaryFile(fileUri);
				processVolumesFromFile(commissaryFile);
				if(!commissaryFile.delete()) {
					MtnLogger.warn("File not deleted!");
				} else {
					MtnLogger.info("Temp file successfully deleted.");
				}
				MtnLogger.info(commissaryFile.getAbsolutePath());
			} else {
				MtnLogger.warn("File link not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File getCommissaryFile(String uri) throws IOException {
		File xlsxFile = File.createTempFile("commissary-data", ".xlsx");
		FileUtils.copyURLToFile(new URL(uri), xlsxFile, 10000, 10000);
		return xlsxFile;
	}

	private void processVolumesFromFile(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			// Sheet of interest is 2nd to last
			Iterator<Sheet> sheetIt = workbook.sheetIterator();

			while (sheetIt.hasNext()) {
				Sheet sheet = sheetIt.next();
				String sheetName = sheet.getSheetName();
				if (sheetName.matches("^[a-zA-Z]{3}\\s\\d{2}$")) {
					LocalDate volumeDate = getDateForSheet(sheetName);
					List<StoreVolume> volumes = this.volumeService.findAllBySourceAndDate("Commissary", volumeDate);
					if (volumes.isEmpty()) {
						processSheet(sheet, volumeDate);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private LocalDate getDateForSheet(String sheetName) {
		return LocalDate.parse("01 " + sheetName, DateTimeFormatter.ofPattern("dd MMM yy"))
				.with(TemporalAdjusters.lastDayOfMonth());
	}

	private void processSheet(Sheet sheet, LocalDate volumeDate) {
		Iterator<Row> rowIt = sheet.iterator();

		String[] arr = {"CUBA", "PUERTO", "GUAM", "JAPAN", "KOREA", "MARSHALL", "OKINAWA"};
		Set<String> stateExclusions = new HashSet<>(Arrays.asList(arr));

		int daysInMonth = volumeDate.getDayOfMonth();

		int count = 0;

		// Skip Heading
		rowIt.next();

		// Process rows
		while (rowIt.hasNext()) {
			Row row = rowIt.next();

			String area = row.getCell(2).getStringCellValue();
			String state = row.getCell(4).getStringCellValue();

			// Skip stores not in USA
			if (!area.isEmpty() && !area.equals("Europe") && !stateExclusions.contains(state.trim().toUpperCase())) {
				double totalMonthlyVolume = row.getCell(9).getNumericCellValue();

				// Skip records without volumes
				if (totalMonthlyVolume != 0.0) {
					int weeklyVolume = (int) Math.round(totalMonthlyVolume / daysInMonth * 7);
					int roundedVolume = Math.round(weeklyVolume / 1000f) * 1000;

					// Department of Defense Activity Address Code
					String dodaac = row.getCell(0).getStringCellValue();
					Optional<StoreSource> storeSourceOpt = storeSourceService.findOneBySourceNativeIdUsingSpecs("Commissary", dodaac);
					if (storeSourceOpt.isPresent()) {
						count++;
						StoreSource storeSource = storeSourceOpt.get();
						StoreVolumeView newVolume = new StoreVolumeView();
						newVolume.setVolumeTotal(roundedVolume);
						newVolume.setVolumeDate(volumeDate);
						newVolume.setSource("Commissary");
						newVolume.setVolumeType(VolumeType.ACTUAL);
						volumeService.addOneToStore(newVolume, storeSource.getStore());
					} else {
						MtnLogger.info("No StoreSource for: " +  volumeDate + "\t" + area + "\t" + state + "\t" + dodaac + "\t" + totalMonthlyVolume + "\t" + roundedVolume);
					}
				}
			}
		}

		MtnLogger.info("Added " + count + " volumes for " + volumeDate);
	}
}
