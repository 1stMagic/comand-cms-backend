package com.biock.cms.page.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.page.MetaData;
import com.biock.cms.page.builder.MetaDataBuilder;
import com.biock.cms.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

@Component
public class MetaDataMapper implements Mapper<MetaData> {

    private final TranslationMapper translationMapper;

    public MetaDataMapper(final TranslationMapper translationMapper) {

        this.translationMapper = translationMapper;
    }

    @Override
    public MetaDataBuilder toEntityBuilder(final Node node) {

        try {
            final MetaDataBuilder builder = new MetaDataBuilder();
            final NodeIterator nodes = node.getNodes();
            while (nodes.hasNext()) {
                final Node translationNode = nodes.nextNode();
                if (CmsType.TRANSLATION.isNodeType(translationNode)) {
                    builder.metaDate(translationNode.getName(), this.translationMapper.toEntity(translationNode));
                }
            }
            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final MetaData entity, final Node node) {

    }

    public MetaData map(final Node node, final String nodeName) {

        try {
            if (node.hasNode(nodeName)) {
                final Node metaDataNode = node.getNode(nodeName);
                if (CmsType.META_DATA.isNodeType(metaDataNode)) {
                    return toEntity(metaDataNode);
                }
            }
            return MetaData.empty();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}