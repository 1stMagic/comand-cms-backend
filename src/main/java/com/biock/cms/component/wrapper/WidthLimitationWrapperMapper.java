package com.biock.cms.component.wrapper;

import com.biock.cms.CmsProperty;
import com.biock.cms.shared.AbstractMapper;
import com.biock.cms.shared.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;

import java.util.Map;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component("widthLimitationWrapper_mapper")
public class WidthLimitationWrapperMapper extends AbstractMapper<WidthLimitationWrapperComponent> {

    private final Map<String, Mapper<? extends com.biock.cms.component.Component>> mappers;

    public WidthLimitationWrapperMapper(final Map<String, Mapper<? extends com.biock.cms.component.Component>> mappers) {

        this.mappers = mappers;
    }

    @Override
    public WidthLimitationWrapperComponent toEntity(final Node node) {

        return WidthLimitationWrapperComponent.builder()
                .name(getStringProperty(node, Property.JCR_NAME))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .componentName(getStringProperty(node, CmsProperty.COMPONENT_NAME))
                .components(getComponents(this.mappers, node))
                .build();
    }

    @Override
    public void toNode(final WidthLimitationWrapperComponent entity, final Node node) {

        throw new UnsupportedOperationException();
    }

}
