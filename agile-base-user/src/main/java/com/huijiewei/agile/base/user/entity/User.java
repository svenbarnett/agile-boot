package com.huijiewei.agile.base.user.entity;

import com.huijiewei.agile.base.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_user")
@DynamicInsert
@DynamicUpdate
public class User extends TimestampEntity {
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

    public static List<CreatedFrom> createFromOptions() {
        List<CreatedFrom> createdFroms = new ArrayList<>();

        createdFroms.add(new CreatedFrom(User.CREATED_FROM_WEB, "网站"));
        createdFroms.add(new CreatedFrom(User.CREATED_FROM_APP, "应用"));
        createdFroms.add(new CreatedFrom(User.CREATED_FROM_WECHAT, "微信"));
        createdFroms.add(new CreatedFrom(User.CREATED_FROM_SYSTEM, "系统"));

        return createdFroms;
    }

    @Data
    public static class CreatedFrom {
        private String value;
        private String label;

        public CreatedFrom(String value, String label) {
            this.value = value;
            this.label = label;
        }
    }
}
