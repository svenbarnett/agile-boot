package com.huijiewei.agile.core.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminResponse extends AdminMiniResponse {
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
