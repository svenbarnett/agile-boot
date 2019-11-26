package com.huijiewei.agile.base.user.mapper;

import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.request.UserRequest;
import com.huijiewei.agile.base.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponses(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdFrom", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    User toUser(UserRequest userRequest, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdFrom", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    User toUser(UserRequest userRequest);

    default Page<UserResponse> toPageResponse(Page<User> page) {
        return page.map(this::toUserResponse);
    }

    default UserResponse.CreatedFrom toCreatedFrom(String createdFrom) {
        Map<String, String> createdFromMap = User.createFromMap();

        if (createdFromMap.containsKey(createdFrom)) {
            return new UserResponse.CreatedFrom(createdFrom, createdFromMap.get(createdFrom));
        }

        return new UserResponse.CreatedFrom(createdFrom, createdFrom);
    }
}
