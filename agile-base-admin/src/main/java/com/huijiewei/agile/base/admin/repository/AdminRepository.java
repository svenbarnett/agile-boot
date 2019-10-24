package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public Admin findByEmail(String email);

    public Admin findByPhone(String phone);
}
