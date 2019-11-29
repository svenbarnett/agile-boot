package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @EntityGraph(attributePaths = "adminGroup")
    List<Admin> findAllWithAdminGroupByOrderByIdAsc();

    Admin findByPhone(String phone);

    Admin findByEmail(String email);

    Boolean existsByAdminGroupId(Integer adminGroupId);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneAndIdNot(String phone, Integer id);

    Boolean existsByEmailAndIdNot(String email, Integer id);
}
