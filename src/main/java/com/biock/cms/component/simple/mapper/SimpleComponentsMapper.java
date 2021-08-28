package com.biock.cms.component.simple.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.component.simple.SimpleComponent;
import com.biock.cms.component.simple.builder.SimpleComponentsBuilder;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.List;

@Component
public class SimpleComponentsMapper implements Mapper<List<SimpleComponent>> {

    private final SimpleComponentMapper componentMapper;

    public SimpleComponentsMapper(final SimpleComponentMapper componentMapper) {

        this.componentMapper = componentMapper;
    }

    @Override
    public SimpleComponentsBuilder toEntityBuilder(final Node node) {

        try {
            final SimpleComponentsBuilder builder = new SimpleComponentsBuilder();
            final NodeIterator nodes = node.getNodes();
            while (nodes.hasNext()) {
                final Node languageNode = nodes.nextNode();
                if (CmsType.COMPONENT.isNodeType(languageNode)) {
                    builder.component(this.componentMapper.toEntity(languageNode));
                }
            }
            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<SimpleComponent> entity, final Node node) {

    }
}
