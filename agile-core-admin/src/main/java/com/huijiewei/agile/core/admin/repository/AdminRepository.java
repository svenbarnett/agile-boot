package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.repository.ValidSaveRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>, ValidSaveRepository<Admin> {
    @EntityGraph(attributePaths = "adminGroup")
    List<Admin> findAllWithAdminGroupByOrderByIdAsc();

    Admin findByPhone(String phone);

    Admin findByEmail(String email);

    Boolean existsByAdminGroupId(Integer adminGroupId);
}
