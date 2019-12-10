package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminLogRepository extends JpaRepository<AdminLog, Integer>, JpaSpecificationExecutor<AdminLog> {
}
