package com.mtn.controller;

import com.mtn.model.domain.ExtractionField;
import com.mtn.model.domain.StoreCasing;
import com.mtn.service.ExtractionFieldService;
import com.mtn.service.StoreCasingService;
import com.mtn.util.csv.FmtBlank;
import com.mtn.util.csv.FmtIntersection;
import com.mtn.util.csv.FmtLocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/extraction")
public class ExtractionController {

	@Autowired
	private StoreCasingService storeCasingService;

	@Autowired
	private ExtractionFieldService extractionFieldService;

	@RequestMapping(method = RequestMethod.GET, params = {"project-id"})
	public void findAll(HttpServletResponse response, @RequestParam("project-id") Integer projectId) {

		String csvFileName = "extraction.csv";

		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		List<StoreCasing> casings = storeCasingService.findAllByProjectId(projectId);

		List<ExtractionField> extractionFields = this.extractionFieldService.findAll();

		final CellProcessor[] processors = extractionFields.stream()
				.map(ef -> {
					if (ef.getExtractionDataType() != null) {
						if (ef.getExtractionDataType().equals("DATE")) {
							return new FmtLocalDateTime();
						} else if (ef.getExtractionDataType().equals("INTERSECTION")) {
							return new FmtIntersection();
						} else if (ef.getExtractionDataType().equals("BLANK")) {
							return new FmtBlank();
						}
					}
					return null;
				})
				.toArray(CellProcessor[]::new);
		final String[] headers = extractionFields.stream()
				.map(ExtractionField::getHeader)
				.toArray(String[]::new);
		final String[] FIELD_MAPPING = extractionFields.stream()
				.map(ExtractionField::getFieldMapping)
				.toArray(String[]::new);

		try {
			ICsvDozerBeanWriter beanWriter = new CsvDozerBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			beanWriter.configureBeanMapping(StoreCasing.class, FIELD_MAPPING);

			beanWriter.writeHeader(headers);

			for (StoreCasing casing : casings) {
				beanWriter.write(casing, processors);
			}
			beanWriter.close();
		} catch (IOException e) {
			response.setStatus(400);
		}
	}

}
