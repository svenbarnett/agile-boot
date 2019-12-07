package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.constraint.Unique;
import com.huijiewei.agile.core.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Admin.TABLE_NAME)
@DynamicInsert
@DynamicUpdate
@Unique.List({
        @Unique(fields = {"phone"}, message = "手机号码已被使用"),
        @Unique(fields = {"email"}, message = "电子邮箱已被使用")
})
public class Admin extends TimestampEntity {
    public static final String TABLE_NAME = "ag_admin";

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "adminGroupId")
    private AdminGroup adminGroup;
}
