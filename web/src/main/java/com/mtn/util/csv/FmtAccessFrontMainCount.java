package com.mtn.util.csv;

import com.mtn.constant.AccessType;
import com.mtn.model.domain.ShoppingCenterAccess;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;

public class FmtAccessFrontMainCount extends CellProcessorAdaptor {

	public FmtAccessFrontMainCount() {
	}

	public FmtAccessFrontMainCount(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		if (o != null) {
			List<ShoppingCenterAccess> accesses = (List<ShoppingCenterAccess>) o;
			return accesses.stream()
					.filter(t -> t.getAccessType().equals(AccessType.FRONT_MAIN))
					.count();
		} else {
			return null;
		}
	}
}
