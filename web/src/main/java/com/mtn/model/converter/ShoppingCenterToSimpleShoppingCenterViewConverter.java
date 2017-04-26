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
        return new SimpleShoppingCenterView(shoppingCenter);
    }
}
