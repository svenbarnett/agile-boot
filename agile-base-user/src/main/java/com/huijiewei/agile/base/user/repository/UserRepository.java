package com.huijiewei.agile.base.user.repository;

import com.huijiewei.agile.base.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    public Optional<User> findFirstByOrderByIdDesc();

    public Boolean existsByPhone(String phone);

    public Boolean existsByEmail(String email);
}
