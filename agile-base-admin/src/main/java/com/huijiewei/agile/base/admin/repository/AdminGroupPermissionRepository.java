package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.AdminGroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminGroupPermissionRepository extends JpaRepository<AdminGroupPermission, Integer> {
    public List<AdminGroupPermission> findAllByAdminGroupId(Integer id);
}
