package com.huijiewei.agile.base.admin.mapper;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.request.AdminRequest;
import com.huijiewei.agile.base.admin.response.AdminBaseResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mapping(target = "adminGroup.permissions", ignore = true)
    AdminBaseResponse toAdminBaseResponse(Admin admin);

    @Mapping(target = "adminGroup.permissions", ignore = true)
    AdminResponse toAdminResponse(Admin admin);

    List<AdminResponse> toAdminResponses(List<Admin> admins);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Admin toAdmin(AdminRequest adminRequest);
}
