package com.mtn.util.csv;

import com.mtn.model.domain.Site;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FmtIntersection extends CellProcessorAdaptor {

	public FmtIntersection() {
	}

	public FmtIntersection(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		Site site = (Site) o;
		String intersection = "";
		if (site.getQuad() != null) {
			intersection = site.getQuad() + " of ";
		}
		if (site.getIntersectionStreetPrimary() != null) {
			intersection += site.getIntersectionStreetPrimary();
		}
		if (site.getIntersectionStreetSecondary() != null) {
			intersection += " & " + site.getIntersectionStreetSecondary();
		}
		return intersection;
	}
}
