package com.huijiewei.agile.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class IdentityResponse<T> {
    @Schema(description = "当前用户")
    private T currentUser;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "访问令牌")
    private String accessToken;

}
