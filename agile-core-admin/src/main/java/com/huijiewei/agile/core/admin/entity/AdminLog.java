package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AdminLog extends BaseEntity {
    private String type;
    private Integer status;
    private String action;
    private String params;
    private String userAgent;
    private String remoteAddr;
    private LocalTime createdAt;
}
