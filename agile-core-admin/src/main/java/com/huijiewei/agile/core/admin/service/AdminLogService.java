package com.huijiewei.agile.core.admin.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.mapper.AdminLogMapper;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.admin.request.AdminLogSearchRequest;
import com.huijiewei.agile.core.admin.response.AdminLogResponse;
import com.huijiewei.agile.core.response.SearchPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdminLogService {
    private final AdminLogRepository adminLogRepository;

    public AdminLogService(AdminLogRepository adminLogRepository) {
        this.adminLogRepository = adminLogRepository;
    }

    public SearchPageResponse<AdminLogResponse> search(Boolean withSearchFields, AdminLogSearchRequest request, Pageable pageable) {
        Specification<AdminLog> adminLogSpecification = request.getSpecification();

        Page<AdminLogResponse> adminLogPage = AdminLogMapper.INSTANCE.toPageResponse(
                this.adminLogRepository.findAll(
                        adminLogSpecification,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sorts.builder().desc("id").build()),
                        EntityGraphUtils.fromAttributePaths("admin.adminGroup")
                )
        );

        SearchPageResponse<AdminLogResponse> response = new SearchPageResponse<>();
        response.setPage(adminLogPage);

        if (withSearchFields != null && withSearchFields) {
            response.setSearchFields(request.getSearchFields());
        }

        return response;
    }
}
