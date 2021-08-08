package com.biock.cms.shared.builder;

public interface Builder<T> {

    Builder<T> apply(final T other);
    T build();
}
