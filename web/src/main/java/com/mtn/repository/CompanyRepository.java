package com.mtn.repository;

import com.mtn.model.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Allen on 6/10/2017.
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("from Company c where lower(c.name) like :name")
    Page<Company> findAllWhereNameLike(String name, Pageable page);

    Company findOneByName(String name);
}
