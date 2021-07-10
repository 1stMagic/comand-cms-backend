package com.biock.cms.site;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.LabelMapper;
import com.biock.cms.shared.Mapper;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class SupportedLanguageMapper implements Mapper<SupportedLanguage> {

    @Override
    public SupportedLanguage toEntity(final Node node) {

        try {
            return SupportedLanguage.builder()
                    .language(node.getName())
                    .title(new LabelMapper(CmsProperty.TITLE).toEntity(node))
                    .tooltip(new LabelMapper(CmsProperty.TOOLTIP).toEntity(node))
                    .build();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final SupportedLanguage supportedLanguage, final Node node) {

        new LabelMapper(CmsProperty.TITLE).toNode(supportedLanguage.getTitle(), node);
        new LabelMapper(CmsProperty.TOOLTIP).toNode(supportedLanguage.getTooltip(), node);
    }
}
