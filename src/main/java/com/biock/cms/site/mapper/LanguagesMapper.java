package com.biock.cms.site.mapper;

import com.biock.cms.CmsType;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.site.Language;
import com.biock.cms.site.builder.LanguagesBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class LanguagesMapper implements Mapper<List<Language>> {

    private final LanguageMapper languageMapper;

    public LanguagesMapper(final LanguageMapper languageMapper) {

        this.languageMapper = languageMapper;
    }

    @Override
    public LanguagesBuilder toEntityBuilder(final Node node) {

        try {
            final LanguagesBuilder builder = new LanguagesBuilder();
            final NodeIterator nodes = node.getNodes();
            while (nodes.hasNext()) {
                final Node languageNode = nodes.nextNode();
                if (CmsType.LANGUAGE.isNodeType(languageNode)) {
                    builder.language(this.languageMapper.toEntity(languageNode));
                }
            }
            return builder;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<Language> entity, final Node node) {

    }

    public List<Language> map(final Node node, final String nodeName) {

        try {
            if (node.hasNode(nodeName)) {
                return toEntity(node.getNode(nodeName));
            }
            return emptyList();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
