package com.mtn.model.converter;

import com.mtn.model.domain.Site;
import com.mtn.model.view.SimpleSiteView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/24/2017.
 */
public class SiteToSimpleSiteViewConverter implements Converter<Site, SimpleSiteView> {

    @Override
    public SimpleSiteView convert(Site site) {
        return SiteToSimpleSiteViewConverter.build(site);
    }

    public static SimpleSiteView build(Site site) {
        SimpleSiteView viewModel = new SimpleSiteView();
        viewModel.setId(site.getId());
        viewModel.setType(site.getType());
        viewModel.setLocationType(site.getLocationType());
        viewModel.setAddress1(site.getAddress1());
        viewModel.setAddress2(site.getAddress2());
        viewModel.setCity(site.getCity());
        viewModel.setState(site.getState());
        viewModel.setPostalCode(site.getPostalCode());
        viewModel.setCounty(site.getCounty());
        viewModel.setCountry(site.getCountry());
        viewModel.setFootprintSqft(site.getFootprintSqft());
        viewModel.setIntersectionStreetPrimary(site.getIntersectionStreetPrimary());
        viewModel.setIntersectionStreetSecondary(site.getIntersectionStreetSecondary());
        viewModel.setIntersectionQuad(site.getIntersectionQuad());
        viewModel.setPositionInCenter(site.getPositionInCenter());
        viewModel.setVersion(site.getVersion());

        if (site.getLocation() != null) {
            viewModel.setLocation(PointToGeoJsonViewConverter.build(site.getLocation()));
        }

        if (site.getShoppingCenter() != null) {
            viewModel.setShoppingCenter(ShoppingCenterToSimpleShoppingCenterViewConverter.build(site.getShoppingCenter()));
        }

        return viewModel;
    }
}
