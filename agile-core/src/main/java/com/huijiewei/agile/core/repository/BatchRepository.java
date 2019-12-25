package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.BaseEntity;

import java.util.List;

public interface BatchRepository<T extends BaseEntity> {
    void batchInsert(List<T> entities);
}
