package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.exception.BadRequestException;
import com.huijiewei.agile.core.response.MessageResponse;
import com.huijiewei.agile.core.response.PageResponse;
import com.huijiewei.agile.core.user.entity.User;
import com.huijiewei.agile.core.user.request.UserRequest;
import com.huijiewei.agile.core.user.request.UserSearchRequest;
import com.huijiewei.agile.core.user.response.UserResponse;
import com.huijiewei.agile.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "user", description = "用户接口")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(
            value = "/users",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户列表", operationId = "userIndex", parameters = {
            @Parameter(name = "name", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "phone", description = "手机号码", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "email", description = "电子邮箱", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "page", description = "分页页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "size", description = "分页大小", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "createdFrom", description = "创建来源", in = ParameterIn.QUERY, schema = @Schema(ref = "UserCreatedFromSearchRequestSchema")),
            @Parameter(name = "createdRange", description = "创建日期区间", in = ParameterIn.QUERY, schema = @Schema(ref = "DateRangeSearchRequestSchema"))
    })
    @ApiResponse(responseCode = "200", description = "用户列表")
    @PreAuthorize("hasPermission('ADMIN', 'user/index')")
    public PageResponse<UserResponse> actionIndex(
            @Parameter(description = "是否返回搜索字段信息") @RequestParam(required = false) Boolean withSearchFields,
            @Parameter(hidden = true) UserSearchRequest userSearchRequest,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.userService.getAll(withSearchFields, userSearchRequest, pageable);
    }

    @GetMapping(
            value = "/users/export",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    @Operation(description = "用户导出", operationId = "userExport", parameters = {
            @Parameter(name = "name", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "phone", description = "手机号码", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "email", description = "电子邮箱", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "createdFrom", description = "创建来源", in = ParameterIn.QUERY, schema = @Schema(ref = "UserCreatedFromSearchRequestSchema")),
            @Parameter(name = "createdRange", description = "创建日期区间", in = ParameterIn.QUERY, schema = @Schema(ref = "DateRangeSearchRequestSchema"))

    })
    @ApiResponse(responseCode = "200", description = "用户导出")
    @PreAuthorize("hasPermission('ADMIN', 'user/export')")
    public ResponseEntity<Resource> actionExport(
            @Parameter(hidden = true) UserSearchRequest userSearchRequest
    ) {
        throw new BadRequestException("方法未实现");
    }

    @GetMapping(
            value = "/users/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户详情", operationId = "userView")
    @ApiResponse(responseCode = "200", description = "用户")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @PreAuthorize("hasPermission('ADMIN', 'user/view, user/edit')")
    public UserResponse actionView(@PathVariable("id") Integer id) {
        return this.userService.getById(id);
    }

    @PostMapping(
            value = "/users",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户新建", operationId = "userCreate")
    @ApiResponse(responseCode = "201", description = "用户")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'user/create')")
    public UserResponse actionCreate(@RequestBody UserRequest request, HttpServletRequest servletRequest) {
        return this.userService.create(request, User.CREATED_FROM_SYSTEM, servletRequest.getRemoteAddr());
    }

    @PutMapping(
            value = "/users/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户编辑", operationId = "userEdit")
    @ApiResponse(responseCode = "200", description = "用户")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'user/edit')")
    public UserResponse actionEdit(@PathVariable("id") Integer id, @RequestBody UserRequest request) {
        return this.userService.edit(id, request);
    }

    @DeleteMapping(
            value = "/users/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户删除", operationId = "userDelete")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @PreAuthorize("hasPermission('ADMIN', 'user/delete')")
    public MessageResponse actionDelete(@PathVariable("id") Integer id) {
        this.userService.delete(id);

        return MessageResponse.of("用户删除成功");
    }
}
