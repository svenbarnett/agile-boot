package com.huijiewei.agile.core.entity;

import com.huijiewei.agile.core.consts.ValueDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class IdentityLog<L extends IdentityLog<L>> extends BaseEntity {
    public static final String TYPE_LOGIN = "LOGIN";
    public static final String TYPE_VISIT = "VISIT";
    public static final String TYPE_OPERATE = "OPERATE";

    public static final Integer STATUS_FAIL = 0;
    public static final Integer STATUS_SUCCESS = 1;

    private String type;
    private Integer status;
    private String method;
    private String action;
    private String params;
    private String userAgent;
    private String remoteAddr;
    private String exception;
    private LocalDateTime createdAt;

    public static List<Status> statusList() {
        List<Status> statuses = new ArrayList<>();

        statuses.add(new Status(STATUS_FAIL, "失败"));
        statuses.add(new Status(STATUS_SUCCESS, "成功"));

        return statuses;
    }

    public static Status getStatus(Integer status) {
        return IdentityLog.statusList()
                .stream()
                .filter(item -> item.getValue().equals(status))
                .findFirst()
                .orElse(new Status(status, status.toString()));
    }

    public static List<Type> typeList() {
        List<Type> types = new ArrayList<>();

        types.add(new Type(TYPE_LOGIN, "登录"));
        types.add(new Type(TYPE_VISIT, "访问"));
        types.add(new Type(TYPE_OPERATE, "操作"));

        return types;
    }

    public static Type getType(String type) {
        return IdentityLog.typeList()
                .stream()
                .filter(item -> item.getValue().equals(type))
                .findFirst()
                .orElse(new Type(type, type));
    }

    public static class Status extends ValueDescription<Integer> {
        public Status(Integer value, String description) {
            super(value, description);
        }
    }

    public static class Type extends ValueDescription<String> {
        public Type(String value, String description) {
            super(value, description);
        }
    }
}
