package com.huijiewei.agile.base.user.service;

import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.response.PageResponse;
import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.mapper.UserMapper;
import com.huijiewei.agile.base.user.repository.UserRepository;
import com.huijiewei.agile.base.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PageResponse<UserResponse> getAll(Integer page, Integer size) {
        Page<UserResponse> users = UserMapper.INSTANCE.toPageResponse(this.userRepository
                .findAll(PageRequest.of(page, size)));

        return new PageResponse<UserResponse>()
                .data(users);
    }

    public UserResponse getById(Integer id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("用户不存在");
        }

        return UserMapper.INSTANCE.toUserResponse(userOptional.get());
    }
}
