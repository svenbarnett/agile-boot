package com.huijiewei.agile.core.admin.mapper;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.request.AdminRequest;
import com.huijiewei.agile.core.admin.response.AdminBaseResponse;
import com.huijiewei.agile.core.admin.response.AdminMiniResponse;
import com.huijiewei.agile.core.admin.response.AdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminBaseResponse toAdminBaseResponse(Admin admin);

    AdminMiniResponse toAdminMiniResponse(Admin admin);

    AdminResponse toAdminResponse(Admin admin);

    List<AdminResponse> toAdminResponses(List<Admin> admins);

    @Mapping(target = "password", ignore = true)
    Admin toAdmin(AdminRequest adminRequest);

    @Mapping(target = "password", ignore = true)
    Admin toAdmin(AdminRequest adminRequest, @MappingTarget Admin admin);
}
