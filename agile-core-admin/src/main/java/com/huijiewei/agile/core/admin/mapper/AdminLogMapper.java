package com.huijiewei.agile.core.admin.mapper;

import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.response.AdminLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminLogMapper {
    AdminLogMapper INSTANCE = Mappers.getMapper(AdminLogMapper.class);

    AdminLogResponse toAdminLogResponse(AdminLog adminLog);

    default Page<AdminLogResponse> toPageResponse(Page<AdminLog> page) {
        return page.map(this::toAdminLogResponse);
    }

    default AdminLog.Type toType(String type) {
        return AdminLog.getType(type);
    }

    default AdminLog.Status toStatus(Integer status) {
        return AdminLog.getStatus(status);
    }
}
