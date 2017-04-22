package com.mtn.model.converter;

import com.mtn.model.domain.UserIdentity;
import com.mtn.model.view.SimpleUserIdentityView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/21/2017.
 */
public class UserIdentityToSimpleUserIdentityViewConverter implements Converter<UserIdentity, SimpleUserIdentityView> {

    @Override
    public SimpleUserIdentityView convert(UserIdentity userIdentity) {
        return UserIdentityToSimpleUserIdentityViewConverter.build(userIdentity);
    }

    public static SimpleUserIdentityView build(UserIdentity userIdentity) {
        SimpleUserIdentityView viewModel = new SimpleUserIdentityView();
        viewModel.setId(userIdentity.getId());
        viewModel.setProvider(userIdentity.getProvider());
        viewModel.setProviderUserId(userIdentity.getProviderUserId());

        return viewModel;
    }
}
