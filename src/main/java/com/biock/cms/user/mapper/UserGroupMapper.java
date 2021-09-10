package com.biock.cms.user.mapper;

import com.biock.cms.CmsNode;
import com.biock.cms.CmsProperty;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.user.UserGroup;
import com.biock.cms.user.builder.UserGroupBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class UserGroupMapper implements Mapper<UserGroup> {

    private final TranslationMapper translationMapper;

    public UserGroupMapper(final TranslationMapper translationMapper) {

        this.translationMapper = translationMapper;
    }

    @Override
    public UserGroupBuilder toEntityBuilder(final Node node) {

        return UserGroup.builder()
                .id(getStringProperty(node, Property.JCR_ID))
                .name(this.translationMapper.map(node, CmsNode.NAME))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE, false));
    }

    @Override
    public void toNode(final UserGroup entity, final Node node) {


    }
}
