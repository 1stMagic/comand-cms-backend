package com.biock.cms.site.mapper;

import com.biock.cms.CmsProperty;
import com.biock.cms.i18n.mapper.TranslationMapper;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.mapper.Mapper;
import com.biock.cms.site.Language;
import com.biock.cms.site.builder.LanguageBuilder;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;
import static com.biock.cms.jcr.PropertyUtils.getStringProperty;

@Component
public class LanguageMapper implements Mapper<Language> {

    private final TranslationMapper translationMapper;

    public LanguageMapper(final TranslationMapper translationMapper) {

        this.translationMapper = translationMapper;
    }

    @Override
    public LanguageBuilder toEntityBuilder(final Node node) {

        try {
            return Language.builder()
                    .id(node.getName())
                    .iso6391Code(getStringProperty(node, CmsProperty.ISO_639_1_CODE))
                    .iso6392Code(getStringProperty(node, CmsProperty.ISO_639_2_CODE))
                    .name(this.translationMapper.map(node, CmsProperty.NAME))
                    .tooltip(this.translationMapper.map(node, CmsProperty.TOOLTIP))
                    .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                    .defaultLanguage(getBooleanProperty(node, CmsProperty.DEFAULT_LANGUAGE));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Language entity, final Node node) {

    }
}
