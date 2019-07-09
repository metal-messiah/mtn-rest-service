package com.mtn.util.csv;

import com.mtn.model.domain.ShoppingCenterTenant;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;
import java.util.stream.Collectors;

public class FmtTenantInlineList extends CellProcessorAdaptor {

	public FmtTenantInlineList() {
	}

	public FmtTenantInlineList(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		if (o != null) {
			List<ShoppingCenterTenant> tenants = (List<ShoppingCenterTenant>) o;
			return tenants.stream()
					.filter(t -> !t.getOutparcel())
					.map(ShoppingCenterTenant::getName)
					.sorted()
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}
}
