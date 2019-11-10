package com.huijiewei.agile.base.admin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminResponse extends AdminBaseResponse {
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
