package com.mtn.repository;

import com.mtn.model.domain.Source;

public interface SourceRepository extends EntityRepository<Source> {

	Source findFirstBySourceName(String sourceName);
}
