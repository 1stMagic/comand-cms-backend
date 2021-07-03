package com.biock.cms.component.header;

import com.biock.cms.CmsProperty;
import com.biock.cms.shared.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;
import static com.biock.cms.utils.LanguageUtils.getLanguageNodePropertyValue;

@Component("header_mapper")
public class HeaderMapper implements Mapper<HeaderComponent> {

    @Override
    public HeaderComponent toEntity(final Node node) {

        return HeaderComponent.builder()
                .name(getStringProperty(node, Property.JCR_NAME))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .componentName(getStringProperty(node, CmsProperty.COMPONENT_NAME))
                .text(getLanguageNodePropertyValue(node, "de", "text"))
                .build();
    }

    @Override
    public void toNode(final HeaderComponent entity, final Node node) {

        throw new UnsupportedOperationException();
    }
}
