package com.mtn.controller;

import com.mtn.model.domain.ExtractionField;
import com.mtn.model.domain.StoreCasing;
import com.mtn.service.ExtractionFieldService;
import com.mtn.service.StoreCasingService;
import com.mtn.util.csv.*;
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
						if (ef.getExtractionDataType().equals("DATE_TIME")) {
							return new FmtLocalDateTime();
						} else if (ef.getExtractionDataType().equals("DATE")) {
							return new FmtLocalDate();
						} else if (ef.getExtractionDataType().equals("INTERSECTION")) {
							return new FmtIntersection();
						} else if (ef.getExtractionDataType().equals("BLANK")) {
							return new FmtBlank();
						} else if (ef.getExtractionDataType().equals("ACCESS_COUNT")) {
							return new FmtAccessCount();
						} else if (ef.getExtractionDataType().equals("ACCESS_FRONT_MAIN_COUNT")) {
							return new FmtAccessFrontMainCount();
						} else if (ef.getExtractionDataType().equals("ACCESS_SIDE_MAIN_COUNT")) {
							return new FmtAccessSideMainCount();
						} else if (ef.getExtractionDataType().equals("ACCESS_NON_MAIN_COUNT")) {
							return new FmtAccessNonMainCount();
						} else if (ef.getExtractionDataType().equals("PROJECT_LIST")) {
							return new FmtProjectList();
						} else if (ef.getExtractionDataType().equals("TENANT_COUNT")) {
							return new FmtTenantCount();
						} else if (ef.getExtractionDataType().equals("TENANT_INLINE_COUNT")) {
							return new FmtTenantInlineCount();
						} else if (ef.getExtractionDataType().equals("TENANT_INLINE_LIST")) {
							return new FmtTenantInlineList();
						} else if (ef.getExtractionDataType().equals("TENANT_OUTPARCEL_COUNT")) {
							return new FmtTenantOutparcelCount();
						} else if (ef.getExtractionDataType().equals("TENANT_OUTPARCEL_LIST")) {
							return new FmtTenantOutparcelList();
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
