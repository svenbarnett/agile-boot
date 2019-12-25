package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.BaseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
public interface ValidSaveRepository<T extends BaseEntity> {
    @Modifying
    @Transactional
    void saveWithValid(@Valid T entity);
}
