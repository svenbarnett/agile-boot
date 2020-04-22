package com.huijiewei.agile.core.admin.repository;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.repository.IdentityRepository;
import com.huijiewei.agile.core.repository.ValidSaveRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends
        EntityGraphJpaRepository<Admin, Integer>,
        ValidSaveRepository<Admin, Integer>,
        IdentityRepository<Admin, Integer> {
    List<Admin> findAll(EntityGraph entityGraph);

    boolean existsByAdminGroupId(Integer adminGroupId);
}
