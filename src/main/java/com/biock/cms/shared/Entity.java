package com.biock.cms.shared;

import java.io.Serializable;

public interface Entity<T> extends Serializable, Comparable<Entity<T>> {

    EntityId getId();
}
