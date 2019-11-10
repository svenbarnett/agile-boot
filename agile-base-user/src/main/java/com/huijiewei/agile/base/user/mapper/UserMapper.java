package com.huijiewei.agile.base.user.mapper;

import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User admin);

    List<UserResponse> toUserResponses(List<User> admins);

    default Page<UserResponse> toPageResponse(Page<User> page) {
        return page.map(this::toUserResponse);
    }

    default UserResponse.CreatedFrom createdFromEnumsToCreatedFrom(String createdFrom) {
        Map<String, String> createdFromMap = User.createFromMap();

        if (createdFromMap.containsKey(createdFrom)) {
            return new UserResponse.CreatedFrom(createdFrom, (String) createdFromMap.get(createdFrom));
        }

        return new UserResponse.CreatedFrom(createdFrom, createdFrom);
    }
}
