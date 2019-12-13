package com.huijiewei.agile.core.user.entity;

import com.huijiewei.agile.core.constraint.Unique;
import com.huijiewei.agile.core.entity.BaseEntity;
import com.huijiewei.agile.core.entity.SoftDeleteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Unique.List({
        @Unique(fields = {"phone"}, message = "手机号码已被使用"),
        @Unique(fields = {"email"}, message = "电子邮箱已被使用")
})
public class User extends BaseEntity {
    public static final String CREATED_FROM_WEB = "WEB";
    public static final String CREATED_FROM_APP = "APP";
    public static final String CREATED_FROM_WECHAT = "WECHAT";
    public static final String CREATED_FROM_SYSTEM = "SYSTEM";

    private String phone;

    private String email;

    private String password;

    private String name;

    private String avatar;

    private String createdIp;

    private String createdFrom;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Map<String, String> createFromMap() {
        Map<String, String> map = new HashMap<>();

        map.put(User.CREATED_FROM_WEB, "网站");
        map.put(User.CREATED_FROM_APP, "应用");
        map.put(User.CREATED_FROM_WECHAT, "微信");
        map.put(User.CREATED_FROM_SYSTEM, "系统");

        return map;
    }

    public static List<String> createFormList() {
        return new ArrayList<>(createFromMap().keySet());
    }
}
