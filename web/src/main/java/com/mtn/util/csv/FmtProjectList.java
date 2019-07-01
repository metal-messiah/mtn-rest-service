package com.mtn.util.csv;

import com.mtn.model.domain.Project;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;
import java.util.stream.Collectors;

public class FmtProjectList extends CellProcessorAdaptor {

	public FmtProjectList() {
	}

	public FmtProjectList(CellProcessor next) {
		super(next);
	}

	@Override
	public Object execute(Object o, CsvContext csvContext) {
		if (o != null) {
			List<Project> projects = (List<Project>) o;
			List<String> projectNames = projects.stream()
					.map(Project::getProjectName)
					.sorted()
					.collect(Collectors.toList());
			if (projectNames.size() == 1) {
				return projectNames.get(0);
			}
			return projectNames;
		} else {
			return null;
		}
	}
}
