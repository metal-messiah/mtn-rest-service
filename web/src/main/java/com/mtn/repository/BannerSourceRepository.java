package com.mtn.repository;

import com.mtn.model.domain.BannerSource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BannerSourceRepository extends EntityRepository<BannerSource> {

    @Query("SELECT max(s.sourceEditedDate) FROM BannerSource s where s.sourceName = :sourceName")
    LocalDateTime getMaxSourceEditedDate(@Param("sourceName") String sourceName);

}
