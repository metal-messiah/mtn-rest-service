package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.service.ExtractionFieldSetService;
import com.mtn.service.StoreCasingService;
import com.mtn.service.StoreService;
import com.mtn.util.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extraction")
public class ExtractionController {

	private final StoreCasingService storeCasingService;
	private final ExtractionFieldSetService extractionFieldSetService;
	private final StoreService storeService;

	@Autowired
	public ExtractionController(StoreCasingService storeCasingService,
								ExtractionFieldSetService extractionFieldSetService,
								StoreService storeService) {
		this.storeCasingService = storeCasingService;
		this.extractionFieldSetService = extractionFieldSetService;
		this.storeService = storeService;
	}

	@GetMapping(params = {"store-ids", "field-set-id"})
	public void downloadLatestForStores(HttpServletResponse response,
										@RequestParam("store-ids") List<Integer> storeIds,
										@RequestParam("field-set-id") Integer fieldSetId) {
		List<Store> stores = this.storeService.findAllByIdsUsingSpecs(storeIds);
		List<StoreCasing> casings = stores.stream()
				.map(store -> store.getCasings().stream()
						.filter(storeCasing -> storeCasing.getDeletedDate() == null)
						.max(Comparator.comparing(StoreCasing::getCasingDate)).orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		// Add a blank casing to allow Dozer to get necessary data
		stores.stream().filter(st -> st.getCasings().size() == 0).forEach(st -> {
			StoreCasing tempCasing = new StoreCasing();
			tempCasing.setStore(st);
			casings.add(tempCasing);
		});

		download(response, casings, fieldSetId);
	}

	@GetMapping(value = "all-casings", params = {"store-ids", "field-set-id"})
	public void downloadAllCasingsForStores(HttpServletResponse response,
											@RequestParam("store-ids") List<Integer> storeIds,
											@RequestParam("field-set-id") Integer fieldSetId,
											@RequestParam("primary-only") Boolean primaryOnly) {
		List<Store> stores = this.storeService.findAllByIdsUsingSpecs(storeIds);
		List<StoreCasing> casings = stores.stream()
				.map(Store::getCasings)
				.reduce((prev, curr) -> {
					prev.addAll(curr);
					return prev;
				}).orElse(new ArrayList<>()).stream()
				.filter(casing -> casing.getDeletedDate() == null)
				.collect(Collectors.toList());

		// If user requests primary data only, filter to only those casings associated to primary data projects
		if (primaryOnly) {
			casings = casings.stream().filter(casing -> casing.getProjects().stream().anyMatch(Project::getPrimaryData))
					.collect(Collectors.toList());
		}

		download(response, casings, fieldSetId);
	}


	@GetMapping(params = {"project-id", "field-set-id"})
	public void downloadProjectCasingData(HttpServletResponse response,
										  @RequestParam("project-id") Integer projectId,
										  @RequestParam("field-set-id") Integer fieldSetId) {
		List<StoreCasing> casings = storeCasingService.findAllByProjectId(projectId);
		download(response, casings, fieldSetId);
	}

	private void download(HttpServletResponse response, List<StoreCasing> casings, Integer fieldSetId) {
		String csvFileName = "extraction.csv";

		response.setContentType("text/csv");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		ExtractionFieldSet fieldSet = this.extractionFieldSetService.findOne(fieldSetId);
		List<ExtractionField> extractionFields = fieldSet.getFields();

		final CellProcessor[] processors = extractionFields.stream()
				.map(field -> {
					if (field.getExtractionDataType() != null) {
						switch (field.getExtractionDataType()) {
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
							case "LATEST_VOLUME_TOTAL":
								return new GetLatestVolumeTotal();
							case "LATEST_VOLUME_DATE":
								return new GetLatestVolumeDate(new FmtLocalDate());
							case "LATEST_VOLUME_TYPE":
								return new GetLatestVolumeType();
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
