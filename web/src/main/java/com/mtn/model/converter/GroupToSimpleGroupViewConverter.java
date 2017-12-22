package com.mtn.model.converter;

import com.mtn.model.domain.Group;
import com.mtn.model.simpleView.SimpleGroupView;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 5/6/2017.
 */
public class GroupToSimpleGroupViewConverter implements Converter<Group, SimpleGroupView> {

    @Override
    public SimpleGroupView convert(Group group) {
        return GroupToSimpleGroupViewConverter.build(group);
    }

    public static SimpleGroupView build(Group group) {
        return new SimpleGroupView(group);
    }
}
