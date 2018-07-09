package com.mtn.util.csv;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterTenant;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;

public class FmtAccessCount extends CellProcessorAdaptor {

	public FmtAccessCount() {
	}

	public FmtAccessCount(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		List<ShoppingCenterAccess> accesses = (List<ShoppingCenterAccess>) o;
		return accesses.size();
	}
}
