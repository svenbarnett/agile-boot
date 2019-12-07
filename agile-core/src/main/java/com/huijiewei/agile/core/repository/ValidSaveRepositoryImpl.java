package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.BaseEntity;

import javax.persistence.EntityManager;

public class ValidSaveRepositoryImpl<T extends BaseEntity> implements ValidSaveRepository<T> {
    private final EntityManager entityManager;

    public ValidSaveRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveWithValid(T entity) {
        this.entityManager.persist(entity);
    }
}
