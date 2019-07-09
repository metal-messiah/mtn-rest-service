package com.mtn.util.csv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FmtLocalDate extends CellProcessorAdaptor {

	public FmtLocalDate() {
	}

	public FmtLocalDate(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		if (o != null) {
			LocalDate date = (LocalDate) o;
			return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} else {
			return null;
		}
	}
}
