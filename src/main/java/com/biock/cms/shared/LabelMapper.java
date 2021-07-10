package com.biock.cms.shared;

import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.regex.Pattern;

public class LabelMapper implements Mapper<Label> {

    private final String prefix;

    public LabelMapper(final String prefix) {

        this.prefix = prefix;
    }

    @Override
    public Label toEntity(final Node node) {

        try {
            final var pattern = Pattern.compile("^\\Q" + this.prefix + "\\E(?:_([a-z]{2}))?$");
            final var builder = Label.builder();
            final var properties = node.getProperties(this.prefix + "*");
            while (properties.hasNext()) {
                final var property = properties.nextProperty();
                final var matcher = pattern.matcher(property.getName());
                if (matcher.matches()) {
                    builder.text(StringUtils.defaultIfBlank(matcher.group(1), Label.FALLBACK_LANGUAGE), property.getString());
                }
            }
            return builder.build();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void toNode(final Label label, final Node node) {

        try {
            for (final var entry : label.getTexts().entrySet()) {
                if (Label.FALLBACK_LANGUAGE.equals(entry.getKey())) {
                    node.setProperty(this.prefix, entry.getValue());
                } else {
                    node.setProperty(this.prefix + "_" + entry.getKey(), entry.getValue());
                }
            }
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
