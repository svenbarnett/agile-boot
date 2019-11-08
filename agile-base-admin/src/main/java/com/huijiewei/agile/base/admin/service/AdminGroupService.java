package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.mapper.AdminGroupMapper;
import com.huijiewei.agile.base.admin.repository.AdminGroupRepository;
import com.huijiewei.agile.base.admin.request.AdminGroupRequest;
import com.huijiewei.agile.base.admin.response.AdminGroupResponse;
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

    public List<AdminGroupResponse> getAll() {
        return AdminGroupMapper.INSTANCE.toAdminGroupResponses(this.adminGroupRepository.findAll());
    }

    public AdminGroupResponse getById(Integer id) {
        Optional<AdminGroup> adminGroupOptional = this.adminGroupRepository.findById(id);

        if (adminGroupOptional.isEmpty()) {
            throw new NotFoundException("管理组不存在");
        }

        return AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroupOptional.get());
    }

    public AdminGroupResponse create(@Valid AdminGroupRequest request) {
        AdminGroup adminGroup = AdminGroupMapper.INSTANCE.toAdminGroup(request);

        this.adminGroupRepository.save(adminGroup);

        return AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroup);
    }
}
