package com.biock.cms.shared.builder;

import java.util.function.Supplier;

public class BuilderAdapter<T> implements Builder<T> {

    private final T result;
    private final Supplier<T> builder;

    public BuilderAdapter() {

        this((T) null);
    }

    public BuilderAdapter(final T result) {

        this.result = result;
        this.builder = null;
    }

    public BuilderAdapter(final Supplier<T> builder) {

        this.result = null;
        this.builder = builder;
    }

    @Override
    public Builder<T> apply(final T other) {

        return null;
    }

    @Override
    public T build() {

        if (this.result != null) {
            return this.result;
        }
        if (this.builder != null) {
            return this.builder.get();
        }
        return null;
    }
}
