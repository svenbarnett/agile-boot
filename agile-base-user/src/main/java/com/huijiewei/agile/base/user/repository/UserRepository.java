package com.huijiewei.agile.base.user.repository;

import com.huijiewei.agile.base.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findFirstByOrderByIdDesc();

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneAndIdNot(String phone, Integer id);

    Boolean existsByEmailAndIdNot(String email, Integer id);
}
