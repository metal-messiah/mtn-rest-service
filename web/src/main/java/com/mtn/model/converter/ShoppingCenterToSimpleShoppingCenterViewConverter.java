package com.mtn.model.converter;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.view.SimpleShoppingCenterView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterToSimpleShoppingCenterViewConverter implements Converter<ShoppingCenter, SimpleShoppingCenterView> {

    @Override
    public SimpleShoppingCenterView convert(ShoppingCenter shoppingCenter) {
        return ShoppingCenterToSimpleShoppingCenterViewConverter.build(shoppingCenter);
    }

    public static SimpleShoppingCenterView build(ShoppingCenter shoppingCenter) {
        SimpleShoppingCenterView viewModel = new SimpleShoppingCenterView();
        viewModel.setId(shoppingCenter.getId());
        viewModel.setName(shoppingCenter.getName());
        viewModel.setOwner(shoppingCenter.getOwner());
        viewModel.setNativeId(shoppingCenter.getNativeId());
        viewModel.setUrl(shoppingCenter.getUrl());
        viewModel.setVersion(shoppingCenter.getVersion());

        return viewModel;
    }
}
