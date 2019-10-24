package com.huijiewei.agile.base.admin.mapper;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminResponse toAdminResponse(Admin admin);

    List<AdminResponse> toAdminResponses(List<Admin> admins);

    Admin toAdmin(AdminResponse adminResponse);
}
