package com.mtn.model.converter;

import com.mtn.model.domain.Role;
import com.mtn.model.simpleView.SimpleRoleView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 5/6/2017.
 */
public class RoleToSimpleRoleViewConverter implements Converter<Role, SimpleRoleView> {

    @Override
    public SimpleRoleView convert(Role role) {
        return RoleToSimpleRoleViewConverter.build(role);
    }

    public static SimpleRoleView build(Role role) {
        return new SimpleRoleView(role);
    }
}
