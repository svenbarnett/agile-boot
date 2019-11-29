package com.huijiewei.agile.core.admin.mapper;

import com.huijiewei.agile.core.admin.entity.AdminGroup;
import com.huijiewei.agile.core.admin.request.AdminGroupRequest;
import com.huijiewei.agile.core.admin.response.AdminGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminGroupMapper {
    AdminGroupMapper INSTANCE = Mappers.getMapper(AdminGroupMapper.class);

    @Mapping(target = "id", ignore = true)
    AdminGroup toAdminGroup(AdminGroupRequest request);

    @Mapping(target = "id", ignore = true)
    AdminGroup toAdminGroup(AdminGroupRequest request, @MappingTarget AdminGroup adminGroup);

    @Mapping(target = "permissions", ignore = true)
    AdminGroupResponse toAdminGroupResponse(AdminGroup adminGroup);

    List<AdminGroupResponse> toAdminGroupResponses(List<AdminGroup> adminGroups);
}
