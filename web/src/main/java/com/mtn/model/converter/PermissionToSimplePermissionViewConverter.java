package com.mtn.model.converter;

import com.mtn.model.domain.auth.Permission;
import com.mtn.model.view.auth.SimplePermissionView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 5/6/2017.
 */
public class PermissionToSimplePermissionViewConverter implements Converter<Permission, SimplePermissionView> {

    @Override
    public SimplePermissionView convert(Permission permission) {
        return PermissionToSimplePermissionViewConverter.build(permission);
    }

    public static SimplePermissionView build(Permission permission) {
        return new SimplePermissionView(permission);
    }
}
