package com.huijiewei.agile.core.admin.mapper;

import com.huijiewei.agile.core.admin.entity.AdminGroup;
import com.huijiewei.agile.core.admin.request.AdminGroupRequest;
import com.huijiewei.agile.core.admin.response.AdminGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminGroupMapper {
    AdminGroupMapper INSTANCE = Mappers.getMapper(AdminGroupMapper.class);

    AdminGroup toAdminGroup(AdminGroupRequest request);

    AdminGroup toAdminGroup(AdminGroupRequest request, @MappingTarget AdminGroup adminGroup);

    AdminGroupResponse toAdminGroupResponse(AdminGroup adminGroup);

    List<AdminGroupResponse> toAdminGroupResponses(List<AdminGroup> adminGroups);
}
