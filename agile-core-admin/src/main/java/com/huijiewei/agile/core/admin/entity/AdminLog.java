package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AdminLog extends BaseEntity {
    public static final String TYPE_LOGIN = "LOGIN";
    public static final String TYPE_VISIT = "VISIT";
    public static final String TYPE_OPERATE = "OPERATE";

    public static final Integer STATUS_NONE = 0;
    public static final Integer STATUS_FAIL = 1;
    public static final Integer STATUS_ERROR = 2;
    public static final Integer STATUS_SUCCESS = 3;

    private String type;
    private Integer status;
    private String action;
    private String params;
    private String userAgent;
    private String remoteAddr;
    private LocalTime createdAt;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private Admin admin;

    public static Map<Integer, String> statusMap() {
        Map<Integer, String> map = new HashMap<>();

        map.put(STATUS_NONE, "无");
        map.put(STATUS_FAIL, "失败");
        map.put(STATUS_ERROR, "错误");
        map.put(STATUS_SUCCESS, "成功");

        return map;
    }

    public static Map<String, String> typeMap() {
        Map<String, String> map = new HashMap<>();

        map.put(TYPE_LOGIN, "登录");
        map.put(TYPE_VISIT, "访问");
        map.put(TYPE_OPERATE, "操作");

        return map;
    }
}
