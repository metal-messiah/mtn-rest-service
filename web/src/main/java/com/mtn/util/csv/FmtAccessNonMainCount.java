package com.mtn.util.csv;

import com.mtn.constant.AccessType;
import com.mtn.model.domain.ShoppingCenterAccess;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;

public class FmtAccessNonMainCount extends CellProcessorAdaptor {

	public FmtAccessNonMainCount() {
	}

	public FmtAccessNonMainCount(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		List<ShoppingCenterAccess> accesses = (List<ShoppingCenterAccess>) o;
		return accesses.stream()
				.filter(t -> t.getAccessType().equals(AccessType.NON_MAIN))
				.count();
	}
}
