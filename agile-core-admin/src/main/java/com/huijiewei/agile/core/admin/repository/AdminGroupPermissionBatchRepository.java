package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminGroupPermission;

import java.util.List;

public interface AdminGroupPermissionBatchRepository {
    public void batchInsert(List<AdminGroupPermission> adminGroupPermissions);
}
