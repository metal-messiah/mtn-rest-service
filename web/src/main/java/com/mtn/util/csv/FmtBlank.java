package com.mtn.util.csv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

public class FmtBlank extends CellProcessorAdaptor {

	public FmtBlank() {
	}

	public FmtBlank(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		return "";
	}
}
