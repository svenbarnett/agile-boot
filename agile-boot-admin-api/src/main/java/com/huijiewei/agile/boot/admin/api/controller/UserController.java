package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.exception.BadRequestException;
import com.huijiewei.agile.base.response.MessageResponse;
import com.huijiewei.agile.base.response.PageResponse;
import com.huijiewei.agile.base.user.entity.User;
import com.huijiewei.agile.base.user.request.UserRequest;
import com.huijiewei.agile.base.user.request.UserSearchRequest;
import com.huijiewei.agile.base.user.response.UserResponse;
import com.huijiewei.agile.base.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;

@RestController
@Tag(name = "user", description = "用户接口")
public class UserController {
    private final UserService userService;
    private final HttpServletRequest servletRequest;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.servletRequest = request;
    }

    @GetMapping(
            value = "/users",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户列表")
    @ApiResponse(responseCode = "200", description = "用户列表")
    @PreAuthorize("hasPermission(#ADMIN, 'user/index')")
    public PageResponse<UserResponse> actionIndex(
            @Parameter(description = "名称") @RequestParam(required = false) String name,
            @Parameter(description = "手机号码") @RequestParam(required = false) String phone,
            @Parameter(description = "电子邮箱") @RequestParam(required = false) String email,
            @Parameter(description = "创建来源") @RequestParam(required = false) List<String> createdFrom,
            @Parameter(description = "创建日期区间") @RequestParam(required = false) List<String> createdRange,
            @Parameter(description = "是否返回搜索字段信息") @RequestParam(required = false) Boolean withSearchFields,
            @Parameter(description = "分页页码") @RequestParam(required = false) Integer page,
            @Parameter(description = "分页大小") @RequestParam(required = false) Integer size,
            @Parameter(hidden = true) UserSearchRequest userSearchRequest,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.userService.getAll(withSearchFields, userSearchRequest, pageable);
    }

    @GetMapping(
            value = "/users/export",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    @Operation(description = "用户导出")
    @ApiResponse(responseCode = "200", description = "用户导出")
    @PreAuthorize("hasPermission(#ADMIN, 'user/export')")
    public ResponseEntity<Resource> actionExport(
            @Parameter(description = "名称") @RequestParam(required = false) String name,
            @Parameter(description = "手机号码") @RequestParam(required = false) String phone,
            @Parameter(description = "电子邮箱") @RequestParam(required = false) String email,
            @Parameter(description = "创建来源") @RequestParam(required = false) List<String> createdFrom,
            @Parameter(description = "创建日期区间") @RequestParam(required = false) List<String> createdRange,
            @Parameter(hidden = true) UserSearchRequest userSearchRequest
    ) {
        throw new BadRequestException("方法未实现");
    }

    @GetMapping(
            value = "/users/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户详情")
    @ApiResponse(responseCode = "200", description = "用户")
    @ApiResponse(responseCode = "404", description = "用户不存在", ref = "Problem")
    @PreAuthorize("hasPermission(#ADMIN, {'user/view', 'user/edit'})")
    public UserResponse actionView(@PathVariable("id") Integer id) {
        return this.userService.getById(id);
    }

    @PostMapping(
            value = "/users",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户新建")
    @ApiResponse(responseCode = "201", description = "用户")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "ConstraintViolationProblem")
    @PreAuthorize("hasPermission(#ADMIN, 'user/create')")
    public UserResponse actionCreate(@RequestBody UserRequest request) {
        return this.userService.create(request, User.CREATED_FROM_SYSTEM, servletRequest.getRemoteAddr());
    }

    @PutMapping(
            value = "/users/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户编辑")
    @ApiResponse(responseCode = "200", description = "用户")
    @ApiResponse(responseCode = "404", description = "用户不存在", ref = "Problem")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "ConstraintViolationProblem")
    @PreAuthorize("hasPermission(#ADMIN, 'user/edit')")
    public UserResponse actionEdit(@PathVariable("id") Integer id, @RequestBody UserRequest request) {
        return this.userService.edit(id, request);
    }

    @DeleteMapping(
            value = "/users/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户删除")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "用户不存在", ref = "Problem")
    @PreAuthorize("hasPermission(#ADMIN, 'user/delete')")
    public MessageResponse actionDelete(@PathVariable("id") Integer id) {
        this.userService.delete(id);

        return MessageResponse.of("用户删除成功");
    }
}
