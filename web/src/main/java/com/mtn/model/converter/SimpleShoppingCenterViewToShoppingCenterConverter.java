package com.mtn.model.converter;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.view.SimpleShoppingCenterView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/23/2017.
 */
public class SimpleShoppingCenterViewToShoppingCenterConverter implements Converter<SimpleShoppingCenterView, ShoppingCenter> {

    @Override
    public ShoppingCenter convert(SimpleShoppingCenterView simpleShoppingCenterView) {
        return SimpleShoppingCenterViewToShoppingCenterConverter.build(simpleShoppingCenterView);
    }

    public static ShoppingCenter build(SimpleShoppingCenterView simpleShoppingCenterView) {
        ShoppingCenter domainModel = new ShoppingCenter();
        domainModel.setId(simpleShoppingCenterView.getId());
        domainModel.setName(simpleShoppingCenterView.getName());
        domainModel.setOwner(simpleShoppingCenterView.getOwner());
        domainModel.setNativeId(simpleShoppingCenterView.getNativeId());
        domainModel.setUrl(simpleShoppingCenterView.getUrl());
        domainModel.setVersion(simpleShoppingCenterView.getVersion());

        return domainModel;
    }
}
