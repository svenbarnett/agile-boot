package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminGroupRepository extends JpaRepository<AdminGroup, Integer> {
}
