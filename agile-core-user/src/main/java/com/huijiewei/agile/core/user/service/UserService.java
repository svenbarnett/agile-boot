package com.huijiewei.agile.core.user.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.SearchPageResponse;
import com.huijiewei.agile.core.user.entity.User;
import com.huijiewei.agile.core.user.mapper.UserMapper;
import com.huijiewei.agile.core.user.repository.UserRepository;
import com.huijiewei.agile.core.user.request.UserRequest;
import com.huijiewei.agile.core.user.request.UserSearchRequest;
import com.huijiewei.agile.core.user.response.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

    public SearchPageResponse<UserResponse> getAll(Boolean withSearchFields, UserSearchRequest searchRequest, Pageable pageable) {
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

    public void exportAll(UserSearchRequest searchRequest, OutputStream outputStream) throws IOException {
        Specification<User> userSpecification = searchRequest.getSpecification();

        List<UserResponse> users = UserMapper.INSTANCE.toUserResponses(this.userRepository.findAll(userSpecification, Sort.by(Sort.Direction.DESC, "id")));

        List<ExcelExportEntity> entities = new ArrayList<>();
        entities.add(new ExcelExportEntity("Id", "id"));
        entities.add(new ExcelExportEntity("手机号码", "phone"));
        entities.add(new ExcelExportEntity("电子邮件", "email"));
        entities.add(new ExcelExportEntity("名称", "name"));
        entities.add(new ExcelExportEntity("注册 IP", "createdIp"));
        entities.add(new ExcelExportEntity("注册来源", "createdFrom.description"));
        entities.add(new ExcelExportEntity("创建时间", "createdAt"));

        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entities, users);
        workbook.setSheetName(0, "用户列表");

        workbook.write(outputStream);

        workbook.close();
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
        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedFrom(createdFrom);
        user.setCreatedIp(createdIp);

        this.userRepository.saveWithValid(user);

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
        User user = UserMapper.INSTANCE.toUser(request, current);

        if (StringUtils.isNotEmpty(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        this.userRepository.saveWithValid(user);

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
