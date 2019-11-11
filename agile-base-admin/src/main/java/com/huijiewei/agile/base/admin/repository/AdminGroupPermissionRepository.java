package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.AdminGroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminGroupPermissionRepository extends JpaRepository<AdminGroupPermission, Integer> {
    public List<AdminGroupPermission> findAllByAdminGroupId(Integer id);

    @Modifying
    @Transactional
    public void deleteAllByAdminGroupId(Integer id);
}
