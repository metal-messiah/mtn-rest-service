package com.mtn.repository;

import com.mtn.model.domain.StoreSource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StoreSourceRepository extends EntityRepository<StoreSource> {

    @Query("SELECT max(s.sourceEditedDate) FROM StoreSource s where s.sourceName = :sourceName")
    LocalDateTime getMaxSourceEditedDate(@Param("sourceName") String sourceName);

    @Query("SELECT s FROM StoreSource s where s.bannerSource.id = :bannerSourceId and s.sourceNativeId not in :hashIds")
    List<StoreSource> findAllOfBannerSourceWhereNativeIdNotInSet(@Param("bannerSourceId") Integer bannerSourceId,
                                                                 @Param("hashIds") Set<String> hashIds);

}
