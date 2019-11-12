package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByPhone(String phone);

    Admin findByEmail(String email);

    Boolean existsByAdminGroupId(Integer adminGroupId);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);
}
