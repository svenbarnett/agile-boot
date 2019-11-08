package com.huijiewei.agile.base.admin.mapper;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.response.AdminBaseResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminBaseResponse toAdminBaseResponse(Admin admin);

    AdminResponse toAdminResponse(Admin admin);

    List<AdminResponse> toAdminResponses(List<Admin> admins);
}
