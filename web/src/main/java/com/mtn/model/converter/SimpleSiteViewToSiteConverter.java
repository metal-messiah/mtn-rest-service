package com.mtn.model.converter;

import com.mtn.model.domain.Site;
import com.mtn.model.view.SimpleSiteView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/24/2017.
 */
public class SimpleSiteViewToSiteConverter implements Converter<SimpleSiteView, Site> {

    @Override
    public Site convert(SimpleSiteView simpleSiteView) {
        return SimpleSiteViewToSiteConverter.build(simpleSiteView);
    }

    public static Site build(SimpleSiteView simpleSiteView) {
        Site domainModel = new Site();
        domainModel.setId(simpleSiteView.getId());
        domainModel.setType(simpleSiteView.getType());
        domainModel.setLocationType(simpleSiteView.getLocationType());
        domainModel.setAddress1(simpleSiteView.getAddress1());
        domainModel.setAddress2(simpleSiteView.getAddress2());
        domainModel.setCity(simpleSiteView.getCity());
        domainModel.setState(simpleSiteView.getState());
        domainModel.setPostalCode(simpleSiteView.getPostalCode());
        domainModel.setCounty(simpleSiteView.getCounty());
        domainModel.setCountry(simpleSiteView.getCountry());
        domainModel.setFootprintSqft(simpleSiteView.getFootprintSqft());
        domainModel.setIntersectionStreetPrimary(simpleSiteView.getIntersectionStreetPrimary());
        domainModel.setIntersectionStreetSecondary(simpleSiteView.getIntersectionStreetSecondary());
        domainModel.setIntersectionQuad(simpleSiteView.getIntersectionQuad());
        domainModel.setPositionInCenter(simpleSiteView.getPositionInCenter());
        domainModel.setVersion(simpleSiteView.getVersion());

        if (simpleSiteView.getLocation() != null) {
            domainModel.setLocation(GeoJsonViewToPointConverter.build(simpleSiteView.getLocation()));
        }

        if (simpleSiteView.getShoppingCenter() != null) {
            domainModel.setShoppingCenter(SimpleShoppingCenterViewToShoppingCenterConverter.build(simpleSiteView.getShoppingCenter()));
        }

        return domainModel;
    }
}
