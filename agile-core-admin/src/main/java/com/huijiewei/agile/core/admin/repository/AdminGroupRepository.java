package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminGroup;
import com.huijiewei.agile.core.repository.ValidSaveRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminGroupRepository extends
        JpaRepository<AdminGroup, Integer>,
        ValidSaveRepository<AdminGroup, Integer> {
}
