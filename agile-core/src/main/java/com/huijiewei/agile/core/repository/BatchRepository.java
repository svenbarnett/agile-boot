package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.BaseEntity;

import java.util.List;

public interface BatchRepository<T extends BaseEntity> {
    default void batchInsert(List<T> entities) {
        if (entities.size() == 0) {
            return;
        }

        this.batchInsertImpl(entities);
    }

    void batchInsertImpl(List<T> entities);
}
