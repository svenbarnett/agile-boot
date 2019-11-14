package com.huijiewei.agile.base.user.service;

import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.response.PageResponse;
import com.huijiewei.agile.base.response.SearchPageResponse;
import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.mapper.UserMapper;
import com.huijiewei.agile.base.user.repository.UserRepository;
import com.huijiewei.agile.base.user.request.UserSearchRequest;
import com.huijiewei.agile.base.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public PageResponse<UserResponse> getAll(Boolean withSearchFields, UserSearchRequest searchRequest, Pageable pageable) {
        Specification<User> userSpecification = searchRequest.getSpecification();

        Page<UserResponse> users = UserMapper.INSTANCE.toPageResponse(
                this.userRepository.findAll(
                        userSpecification,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sorts.builder().desc("id").build())
                )
        );

        SearchPageResponse<UserResponse> response = new SearchPageResponse<>();
        response.setPage(users);

        if (withSearchFields != null && withSearchFields) {
            response.setSearchFields(searchRequest.getSearchFields());
        }

        return response;
    }

    public UserResponse getById(Integer id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new NotFoundException("用户不存在");
        }

        return UserMapper.INSTANCE.toUserResponse(userOptional.get());
    }
}
