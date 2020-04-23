package com.huijiewei.agile.core.user.mapper;

import com.huijiewei.agile.core.user.entity.User;
import com.huijiewei.agile.core.user.request.UserRequest;
import com.huijiewei.agile.core.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponses(List<User> users);

    @Mapping(target = "password", ignore = true)
    User toUser(UserRequest userRequest, @MappingTarget User user);

    @Mapping(target = "password", ignore = true)
    User toUser(UserRequest userRequest);

    default Page<UserResponse> toPageResponse(Page<User> page) {
        return page.map(this::toUserResponse);
    }

    default User.CreatedFrom toCreatedFrom(String createdFrom) {
        return User.getCreatedFrom(createdFrom);
    }
}
