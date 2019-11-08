package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.response.ListResponse;
import com.huijiewei.agile.base.response.PageResponse;
import com.huijiewei.agile.base.user.response.UserResponse;
import com.huijiewei.agile.base.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@Tag(name = "user", description = "用户接口")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "用户列表", responses = {
            @ApiResponse(responseCode = "200", description = "用户列表")
    })
    @GetMapping(
            value = "/users",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public PageResponse<UserResponse> actionList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        return this.userService.getAll(page, size);
    }

    @GetMapping(
            value = "/users/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户详情", responses = {
            @ApiResponse(responseCode = "200", description = "用户"),
            @ApiResponse(responseCode = "404", description = "用户不存在", ref = "Problem")
    })
    public UserResponse actionDetail(@PathVariable("id") Integer id) {
        return this.userService.getById(id);
    }
}
