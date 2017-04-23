package com.mtn.model.converter;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.view.ShoppingCenterView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterToShoppingCenterViewConverter implements Converter<ShoppingCenter, ShoppingCenterView> {

    @Override
    public ShoppingCenterView convert(ShoppingCenter shoppingCenter) {
        return ShoppingCenterToShoppingCenterViewConverter.build(shoppingCenter);
    }

    public static ShoppingCenterView build(ShoppingCenter shoppingCenter) {
        ShoppingCenterView viewModel = new ShoppingCenterView();
        viewModel.setId(shoppingCenter.getId());
        viewModel.setName(shoppingCenter.getName());
        viewModel.setOwner(shoppingCenter.getOwner());
        viewModel.setNativeId(shoppingCenter.getNativeId());
        viewModel.setUrl(shoppingCenter.getUrl());
        viewModel.setVersion(shoppingCenter.getVersion());
        viewModel.setCreatedDate(shoppingCenter.getCreatedDate());
        viewModel.setUpdatedDate(shoppingCenter.getUpdatedDate());

        viewModel.setCreatedBy(UserProfileToSimpleUserProfileViewConverter.build(shoppingCenter.getCreatedBy()));
        viewModel.setUpdatedBy(UserProfileToSimpleUserProfileViewConverter.build(shoppingCenter.getUpdatedBy()));

        return viewModel;
    }
}
