package com.huijiewei.agile.base.user.service;

import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.base.exception.BadRequestException;
import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.response.PageResponse;
import com.huijiewei.agile.base.response.SearchPageResponse;
import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.mapper.UserMapper;
import com.huijiewei.agile.base.user.repository.UserRepository;
import com.huijiewei.agile.base.user.request.UserRequest;
import com.huijiewei.agile.base.user.request.UserSearchRequest;
import com.huijiewei.agile.base.user.response.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    public void exportAll(UserSearchRequest searchRequest) {
        Specification<User> userSpecification = searchRequest.getSpecification();

        List<UserResponse> users = UserMapper.INSTANCE.toUserResponses(this.userRepository.findAll(userSpecification));
    }

    public UserResponse getById(Integer id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("用户不存在");
        }

        return UserMapper.INSTANCE.toUserResponse(userOptional.get());
    }

    @Validated(UserRequest.Create.class)
    public UserResponse create(@Valid UserRequest request, String createdFrom, String createdIp) {
        if (this.userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("手机号码已被使用");
        }

        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("电子邮箱已被使用");
        }

        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedFrom(createdFrom);
        user.setCreatedIp(createdIp);

        this.userRepository.save(user);

        return UserMapper.INSTANCE.toUserResponse(user);
    }

    @Validated(UserRequest.Edit.class)
    public UserResponse edit(Integer id, @Valid UserRequest request) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("用户不存在");
        }

        User current = userOptional.get();

        User user = this.update(current, request);

        return UserMapper.INSTANCE.toUserResponse(user);
    }


    private User update(User current, UserRequest request) {
        if (this.userRepository.existsByPhoneAndIdNot(request.getPhone(), current.getId())) {
            throw new BadRequestException("手机号码已被使用");
        }

        if (this.userRepository.existsByEmailAndIdNot(request.getEmail(), current.getId())) {
            throw new BadRequestException("电子邮箱已被使用");
        }

        User user = UserMapper.INSTANCE.toUser(request, current);

        if (!StringUtils.isEmpty(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        this.userRepository.save(user);

        return user;
    }

    public void delete(Integer id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("用户不存在");
        }

        this.userRepository.delete(userOptional.get());
    }
}
