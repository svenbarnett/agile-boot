package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.AdminGroupPermission;

import java.util.List;

public interface AdminGroupPermissionBatchRepository {
    public void batchInsert(List<AdminGroupPermission> adminGroupPermissions);
}
