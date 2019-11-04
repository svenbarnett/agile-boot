package com.huijiewei.agile.base.entity;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public interface DeletedEntity {
    static final String NOT_DELETED = "deletedAt IS NULL";

    Instant deletedAt = null;

    public default Boolean isDeleted() {
        return this.deletedAt == null;
    }
}
