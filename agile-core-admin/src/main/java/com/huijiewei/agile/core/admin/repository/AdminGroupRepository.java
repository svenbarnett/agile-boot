package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface AdminGroupRepository extends JpaRepository<AdminGroup, Integer> {
    default Map<Integer, String> findMap() {
        return findAll().stream().collect(Collectors.toMap(AdminGroup::getId, AdminGroup::getName));
    }
}
