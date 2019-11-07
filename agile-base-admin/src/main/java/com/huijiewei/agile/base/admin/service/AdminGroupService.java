package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.repository.AdminGroupRepository;
import com.huijiewei.agile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdminGroupService {
    private final AdminGroupRepository adminGroupRepository;

    @Autowired
    public AdminGroupService(AdminGroupRepository adminGroupRepository) {
        this.adminGroupRepository = adminGroupRepository;
    }

    public List<AdminGroup> getAll() {
        return this.adminGroupRepository.findAll();
    }

    public AdminGroup getById(Integer id) {
        Optional<AdminGroup> adminGroupOptional = this.adminGroupRepository.findById(id);

        if (adminGroupOptional.isEmpty()) {
            throw new NotFoundException("管理组不存在");
        }

        return adminGroupOptional.get();
    }

    public AdminGroup create(@Valid AdminGroup adminGroup) {
        this.adminGroupRepository.save(adminGroup);

        return adminGroup;
    }
}
