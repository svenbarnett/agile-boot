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
@Table(name = AdminLog.TABLE_NAME)
public class AdminLog extends BaseEntity {
    public static final String TABLE_NAME = "ag_admin_log";

    private String type;
    private Integer status;
    private String action;
    private String params;
    private String userAgent;
    private String remoteAddr;
    private LocalTime createdAt;
}
