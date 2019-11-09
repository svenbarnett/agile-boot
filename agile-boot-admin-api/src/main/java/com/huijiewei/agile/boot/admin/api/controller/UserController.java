package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.response.PageResponse;
import com.huijiewei.agile.base.user.request.UserSearchRequest;
import com.huijiewei.agile.base.user.response.UserResponse;
import com.huijiewei.agile.base.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@Tag(name = "user", description = "用户接口")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "用户列表")
    @ApiResponse(responseCode = "200", description = "用户列表")
    @GetMapping(
            value = "/users",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public PageResponse<UserResponse> actionList(
            @Parameter(name = "withSearchFields", required = false, description = "是否返回搜索字段信息") @RequestParam(required = false) Boolean withSearchFields,
            @Parameter(description = "搜索信息", required = false) UserSearchRequest userSearchRequest,
            @Parameter(name = "page", required = false, description = "分页页码") @RequestParam(required = false) Integer page,
            @Parameter(name = "size", required = false, description = "分页大小") @RequestParam(required = false) Integer size,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.userService.getAll(withSearchFields, userSearchRequest, pageable);
    }

    @Operation(description = "用户详情")
    @ApiResponse(responseCode = "200", description = "用户")
    @ApiResponse(responseCode = "404", description = "用户不存在", ref = "Problem")
    @GetMapping(
            value = "/users/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public UserResponse actionDetail(@PathVariable("id") Integer id) {
        return this.userService.getById(id);
    }
}
