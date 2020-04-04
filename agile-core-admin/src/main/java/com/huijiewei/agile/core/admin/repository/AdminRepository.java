package com.huijiewei.agile.core.admin.repository;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.repository.ValidSaveRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends EntityGraphJpaRepository<Admin, Integer>, ValidSaveRepository<Admin> {
    List<Admin> findAll(EntityGraph entityGraph);

    Admin findByPhone(String phone);

    Admin findByEmail(String email);

    Boolean existsByAdminGroupId(Integer adminGroupId);
}
