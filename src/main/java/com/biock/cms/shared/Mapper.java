package com.biock.cms.shared;

import javax.jcr.Node;
import javax.validation.constraints.NotNull;

public interface Mapper<E> {

    default E toEntity(@NotNull final Node node) {

        return toEntityBuilder(node).build();
    }

    Builder<E> toEntityBuilder(@NotNull final Node node);

    void toNode(@NotNull final E entity, @NotNull final Node node);
}
