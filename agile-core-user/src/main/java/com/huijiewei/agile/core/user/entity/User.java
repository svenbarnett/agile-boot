package com.huijiewei.agile.core.user.entity;

import com.huijiewei.agile.core.constraint.Unique;
import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static List<CreatedFrom> createFromList() {
        List<CreatedFrom> createdFroms = new ArrayList<>();

        createdFroms.add(new CreatedFrom(CREATED_FROM_WEB, "网站"));
        createdFroms.add(new CreatedFrom(CREATED_FROM_APP, "应用"));
        createdFroms.add(new CreatedFrom(CREATED_FROM_WECHAT, "微信"));
        createdFroms.add(new CreatedFrom(CREATED_FROM_SYSTEM, "系统"));

        return createdFroms;
    }

    public static CreatedFrom getCreateFrom(String createdFrom) {
        List<CreatedFrom> createdFroms = User.createFromList();

        for (CreatedFrom everyCreateFrom : createdFroms) {
            if (everyCreateFrom.value.equals(createdFrom)) {
                return everyCreateFrom;
            }
        }

        return new CreatedFrom(createdFrom, createdFrom);
    }

    public static List<String> createFormValues() {
        List<CreatedFrom> createdFroms = User.createFromList();
        List<String> createFormValues = new ArrayList<>();

        for (CreatedFrom everyCreateFrom : createdFroms) {
            createFormValues.add(everyCreateFrom.value);
        }

        return createFormValues;
    }

    @Data
    public static class CreatedFrom {
        private String value;
        private String description;

        public CreatedFrom(String value, String label) {
            this.value = value;
            this.description = label;
        }
    }
}
