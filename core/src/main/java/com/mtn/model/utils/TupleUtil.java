package com.mtn.model.utils;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TupleUtil {

	public static Map<String, Object> toMap(Tuple tuple) {
		return tuple.getElements().stream().collect(Collectors.toMap(TupleElement::getAlias, t -> tuple.get(t.getAlias())));
	}

	public static List<Map<String, Object>> toListOfMaps(List<Tuple> tuples) {
		return tuples.stream().map(TupleUtil::toMap).collect(Collectors.toList());
	}
}
