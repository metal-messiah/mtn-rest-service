package com.mtn.model.converter;

import com.mtn.model.domain.Company;
import com.mtn.model.view.SimpleCompanyView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 6/10/2017.
 */
public class CompanyToSimpleCompanyViewConverter implements Converter<Company, SimpleCompanyView> {

    @Override
    public SimpleCompanyView convert(Company company) {
        return CompanyToSimpleCompanyViewConverter.build(company);
    }

    public static SimpleCompanyView build(Company company) {
        return new SimpleCompanyView(company);
    }
}
