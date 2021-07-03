package com.biock.cms.shared;

import javax.jcr.Node;
import javax.validation.constraints.NotNull;

public interface Mapper<E> {

    E toEntity(@NotNull final Node node);

    void toNode(@NotNull final E entity, @NotNull final Node node);
}
