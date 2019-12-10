package com.huijiewei.agile.core.admin.mapper;

import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.response.AdminLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Map;

@Mapper
public interface AdminLogMapper {
    AdminLogMapper INSTANCE = Mappers.getMapper(AdminLogMapper.class);

    AdminLogResponse toAdminLogResponse(AdminLog adminLog);

    default Page<AdminLogResponse> toPageResponse(Page<AdminLog> page) {
        return page.map(this::toAdminLogResponse);
    }

    default AdminLogResponse.Type toType(String type) {
        Map<String, String> typeMap = AdminLog.typeMap();

        if (typeMap.containsKey(type)) {
            return new AdminLogResponse.Type(type, typeMap.get(type));
        }

        return new AdminLogResponse.Type(type, type);
    }

    default AdminLogResponse.Status toStatus(Integer status) {
        Map<Integer, String> statusMap = AdminLog.statusMap();

        if (statusMap.containsKey(status)) {
            return new AdminLogResponse.Status(status, statusMap.get(status));
        }

        return new AdminLogResponse.Status(status, status.toString());
    }
}
