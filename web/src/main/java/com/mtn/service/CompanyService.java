package com.mtn.service;

import com.mtn.model.domain.Company;
import com.mtn.repository.CompanyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Allen on 6/10/2017.
 */
@Service
public class CompanyService extends ValidatingDataService<Company> {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public Company addOne(Company request) {
        validateForInsert(request);

        if (request.getParentCompany() != null && request.getParentCompany().getId() != null) {
            Company parent = findOne(request.getParentCompany().getId());
            if (parent == null) {
                throw new IllegalArgumentException("No Company found with this parent company id");
            }

            request.setParentCompany(parent);
            parent.getChildCompanies().add(request);
        }

        return companyRepository.save(request);
    }

    public Page<Company> findAll(Pageable page) {
        return companyRepository.findAll(page);
    }

    public Set<Integer> findAllChildCompanyIdsRecursive(Company company) {
        Set<Integer> ids = new HashSet<>();
        ids.add(company.getId());

        for (Company childCompany : company.getChildCompanies()) {
            ids.addAll(findAllChildCompanyIdsRecursive(childCompany));
        }

        return ids;
    }

    public Page<Company> findAllWhereNameLike(String name, Pageable page) {
        name = String.format("%%%s%%", name.toLowerCase());
        return companyRepository.findAllWhereNameLike(name, page);
    }

    public Company findOne(Integer id) {
        return companyRepository.findOne(id);
    }

    public Company findOneByName(String name) {
        return companyRepository.findOneByName(name);
    }

    @Transactional
    public Company updateOne(Integer id, Company request) {
        validateNotNull(request);
        validateForUpdate(request);

        Company existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Company found with this id");
        }

        existing.setName(request.getName());
        existing.setWebsiteUrl(request.getWebsiteUrl());

        return existing;
    }

    @Transactional
    public Company updateOneParentCompany(Integer childCompanyId, Integer parentCompanyId) {
        Company child = findOne(childCompanyId);
        if (child == null) {
            throw new IllegalArgumentException("No Company found for this child id");
        }

        Company parent = findOne(parentCompanyId);
        if (parent == null) {
            throw new IllegalArgumentException("No Company found for this parent id");
        }

        child.setParentCompany(parent);
        parent.getChildCompanies().add(child);

        return child;
    }

    @Override
    public String getEntityName() {
        return "Company";
    }

    @Override
    public void validateBusinessRules(Company object) {
        if (StringUtils.isBlank(object.getName())) {
            throw new IllegalArgumentException("Company name must be provided");
        }
    }

    @Override
    public void validateForUpdate(Company object) {
        super.validateForUpdate(object);
        validateDoesNotExist(object);
    }

    @Override
    public void validateDoesNotExist(Company object) {
        Company existing = findOneByName(object.getName());
        if (existing != null && object.getId().equals(existing.getId())) {
            throw new IllegalArgumentException("Company with this name already exists");
        }
    }
}
