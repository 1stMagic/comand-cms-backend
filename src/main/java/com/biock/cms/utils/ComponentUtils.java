package com.biock.cms.utils;

import com.biock.cms.CmsProperty;
import com.biock.cms.component.CmsComponent;
import com.biock.cms.component.Component;
import com.biock.cms.shared.Label;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public final class ComponentUtils {

    private ComponentUtils() {

        // Empty
    }

    public static Optional<Label> getHeadline(@NotNull final List<Component> components) {

        final var headline = components.stream()
                .filter(component -> component.isA(CmsComponent.HEADLINE))
                .map(component -> component.findFirst(CmsComponent.H1))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        if (headline.isPresent()) {
            final var innerHtml = headline.get().getProperty(CmsProperty.INNER_HTML);
            if (innerHtml.isPresent()) {
                return Optional.of(innerHtml.get().getValueAs(Label.class));
            }
        }
        return Optional.empty();
    }
}
