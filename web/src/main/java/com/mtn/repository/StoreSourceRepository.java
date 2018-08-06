package com.mtn.repository;

import com.mtn.model.domain.StoreSource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface StoreSourceRepository extends EntityRepository<StoreSource> {

    @Query("SELECT max(s.sourceEditedDate) FROM StoreSource s where s.sourceName = :sourceName")
    LocalDateTime getMaxSourceEditedDate(@Param("sourceName") String sourceName);

}
