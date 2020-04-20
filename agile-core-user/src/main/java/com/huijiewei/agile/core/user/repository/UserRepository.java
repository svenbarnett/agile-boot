package com.huijiewei.agile.core.user.repository;

import com.huijiewei.agile.core.repository.ValidSaveRepository;
import com.huijiewei.agile.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User>,
        ValidSaveRepository<User, Integer> {
    Optional<User> findFirstByOrderByIdDesc();

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);
}
