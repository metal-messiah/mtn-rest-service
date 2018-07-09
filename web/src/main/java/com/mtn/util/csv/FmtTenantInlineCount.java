package com.mtn.util.csv;

import com.mtn.model.domain.ShoppingCenterTenant;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;

public class FmtTenantInlineCount extends CellProcessorAdaptor {

	public FmtTenantInlineCount() {
	}

	public FmtTenantInlineCount(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		List<ShoppingCenterTenant> tenants = (List<ShoppingCenterTenant>) o;
		return tenants.stream()
				.filter(t -> !t.getOutparcel())
				.count();
	}
}
