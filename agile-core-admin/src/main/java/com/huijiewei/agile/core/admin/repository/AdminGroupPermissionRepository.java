package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminGroupPermission;
import com.huijiewei.agile.core.repository.BatchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminGroupPermissionRepository extends
        JpaRepository<AdminGroupPermission, Integer>,
        BatchRepository<AdminGroupPermission> {
    List<AdminGroupPermission> findAllByAdminGroupId(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM AdminGroupPermission WHERE adminGroupId = ?1")
    void deleteAllByAdminGroupId(Integer id);
}
