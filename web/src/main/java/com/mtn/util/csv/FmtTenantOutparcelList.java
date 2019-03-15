package com.mtn.util.csv;

import com.mtn.model.domain.ShoppingCenterTenant;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;
import java.util.stream.Collectors;

public class FmtTenantOutparcelList extends CellProcessorAdaptor {

	public FmtTenantOutparcelList() {
	}

	public FmtTenantOutparcelList(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		List<ShoppingCenterTenant> tenants = (List<ShoppingCenterTenant>) o;
		return tenants.stream()
				.filter(ShoppingCenterTenant::getOutparcel)
				.map(ShoppingCenterTenant::getName)
				.sorted()
				.collect(Collectors.toList());
	}
}
