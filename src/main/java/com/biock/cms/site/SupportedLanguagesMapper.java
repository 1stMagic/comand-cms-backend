package com.biock.cms.site;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class SupportedLanguagesMapper implements Mapper<List<SupportedLanguage>> {

    @Override
    public List<SupportedLanguage> toEntity(final Node node) {

        try {
            final List<SupportedLanguage> supportedLanguages = new ArrayList<>();
            final var supportedLanguageMapper = new SupportedLanguageMapper();
            final NodeIterator supportedLanguageNodes = node.getNodes();
            while (supportedLanguageNodes.hasNext()) {
                supportedLanguages.add(supportedLanguageMapper.toEntity(supportedLanguageNodes.nextNode()));
            }
            return supportedLanguages;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final List<SupportedLanguage> entity, final Node node) {

        try {
            final var supportedLanguageMapper = new SupportedLanguageMapper();
            for (final SupportedLanguage supportedLanguage : entity) {
                final Node languageNode = node.hasNode(supportedLanguage.getLanguage())
                        ? node.getNode(supportedLanguage.getLanguage())
                        : node.addNode(supportedLanguage.getLanguage());
                supportedLanguageMapper.toNode(supportedLanguage, languageNode);
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
