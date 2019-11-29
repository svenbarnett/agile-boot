package com.huijiewei.agile.core.user.entity;

import com.huijiewei.agile.core.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = User.TABLE_NAME)
@DynamicInsert
@DynamicUpdate
public class User extends TimestampEntity {
    public static final String TABLE_NAME = "ag_user";

    public static final String CREATED_FROM_WEB = "WEB";
    public static final String CREATED_FROM_APP = "APP";
    public static final String CREATED_FROM_WECHAT = "WECHAT";
    public static final String CREATED_FROM_SYSTEM = "SYSTEM";

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String avatar;

    private String createdIp;

    private String createdFrom;

    public static Map<String, String> createFromMap() {
        Map<String, String> map = new HashMap<>();

        map.put(User.CREATED_FROM_WEB, "网站");
        map.put(User.CREATED_FROM_APP, "应用");
        map.put(User.CREATED_FROM_WECHAT, "微信");
        map.put(User.CREATED_FROM_SYSTEM, "系统");

        return map;
    }
}
