package com.huijiewei.agile.base.user.mapper;

import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User admin);

    List<UserResponse> toUserResponses(List<User> admins);

    default Page<UserResponse> toPageResponse(Page<User> page) {
        return page.map(this::toUserResponse);
    }

    default UserResponse.CreatedFrom createdFromEnumsToCreatedFrom(User.CreatedFromEnums type) {
        return new UserResponse.CreatedFrom(type.name(), type.getDescription());
    }
}
