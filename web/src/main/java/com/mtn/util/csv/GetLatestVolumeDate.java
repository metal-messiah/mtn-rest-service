package com.mtn.util.csv;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreVolume;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.Comparator;
import java.util.Optional;

public class GetLatestVolumeDate extends CellProcessorAdaptor {

	public GetLatestVolumeDate() {
	}

	public GetLatestVolumeDate(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		Store store = (Store) o;
		Optional<StoreVolume> volume = store.getVolumes().stream()
				.filter(storeVolume -> storeVolume.getDeletedDate() == null)
				.max(Comparator.comparing(StoreVolume::getVolumeDate));
		return volume.map(StoreVolume::getVolumeDate).orElse(null);
	}
}
