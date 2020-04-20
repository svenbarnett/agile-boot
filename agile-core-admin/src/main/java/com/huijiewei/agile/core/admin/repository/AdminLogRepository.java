package com.huijiewei.agile.core.admin.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.huijiewei.agile.core.admin.entity.AdminLog;

public interface AdminLogRepository extends
        EntityGraphJpaRepository<AdminLog, Integer>,
        EntityGraphJpaSpecificationExecutor<AdminLog> {
}
