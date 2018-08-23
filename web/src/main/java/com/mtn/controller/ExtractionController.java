package com.mtn.controller;

import com.mtn.model.domain.ExtractionField;
import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.model.domain.StoreCasing;
import com.mtn.service.ExtractionFieldSetService;
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

	private final StoreCasingService storeCasingService;
	private final ExtractionFieldSetService extractionFieldSetService;

	@Autowired
	public ExtractionController(StoreCasingService storeCasingService, ExtractionFieldSetService extractionFieldSetService) {
		this.storeCasingService = storeCasingService;
		this.extractionFieldSetService = extractionFieldSetService;
	}

	@RequestMapping(method = RequestMethod.GET, params = {"project-id", "field-set-id"})
	public void findAll(HttpServletResponse response,
						@RequestParam("project-id") Integer projectId,
						@RequestParam("field-set-id") Integer fieldSetId) {

		String csvFileName = "extraction.csv";

		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		List<StoreCasing> casings = storeCasingService.findAllByProjectId(projectId);

		ExtractionFieldSet fieldSet = this.extractionFieldSetService.findOne(fieldSetId);
		List<ExtractionField> extractionFields = fieldSet.getFields();

		final CellProcessor[] processors = extractionFields.stream()
				.map(ef -> {
					if (ef.getExtractionDataType() != null) {
						switch (ef.getExtractionDataType()) {
							case "DATE_TIME":
								return new FmtLocalDateTime();
							case "DATE":
								return new FmtLocalDate();
							case "INTERSECTION":
								return new FmtIntersection();
							case "BLANK":
								return new FmtBlank();
							case "ACCESS_COUNT":
								return new FmtAccessCount();
							case "ACCESS_FRONT_MAIN_COUNT":
								return new FmtAccessFrontMainCount();
							case "ACCESS_SIDE_MAIN_COUNT":
								return new FmtAccessSideMainCount();
							case "ACCESS_NON_MAIN_COUNT":
								return new FmtAccessNonMainCount();
							case "PROJECT_LIST":
								return new FmtProjectList();
							case "TENANT_COUNT":
								return new FmtTenantCount();
							case "TENANT_INLINE_COUNT":
								return new FmtTenantInlineCount();
							case "TENANT_INLINE_LIST":
								return new FmtTenantInlineList();
							case "TENANT_OUTPARCEL_COUNT":
								return new FmtTenantOutparcelCount();
							case "TENANT_OUTPARCEL_LIST":
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
