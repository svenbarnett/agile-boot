package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ValidSaveRepositoryImpl<T extends BaseEntity> implements ValidSaveRepository<T> {
    private final EntityManager entityManager;

    @Autowired
    public ValidSaveRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveWithValid(T entity) {
        this.entityManager.persist(entity);
    }
}
