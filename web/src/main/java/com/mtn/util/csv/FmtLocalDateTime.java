package com.mtn.util.csv;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.UserProfile;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FmtLocalDateTime extends CellProcessorAdaptor {

	public FmtLocalDateTime() {
	}

	public FmtLocalDateTime(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		LocalDateTime date = (LocalDateTime) o;
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
