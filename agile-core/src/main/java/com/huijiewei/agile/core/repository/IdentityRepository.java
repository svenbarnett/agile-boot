package com.huijiewei.agile.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IdentityRepository<T, ID> extends CrudRepository<T, ID> {
    Optional<T> findByPhone(String phone);

    Optional<T> findByEmail(String email);
}
