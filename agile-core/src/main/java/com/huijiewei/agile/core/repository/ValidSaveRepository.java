package com.huijiewei.agile.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@NoRepositoryBean
public interface ValidSaveRepository<T, ID> extends CrudRepository<T, ID> {
    @Modifying
    @Transactional
    default void saveWithValid(@Valid T entity) {
        this.save(entity);
    }
}
