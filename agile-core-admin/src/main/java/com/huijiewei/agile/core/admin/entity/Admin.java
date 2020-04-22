package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.constraint.Unique;
import com.huijiewei.agile.core.entity.Identity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Unique.List({
        @Unique(fields = {"phone"}, message = "手机号码已被使用"),
        @Unique(fields = {"email"}, message = "电子邮箱已被使用")
})
public class Admin extends Identity<Admin> {
    private String name;

    private String avatar;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer adminGroupId;

    @ManyToOne
    @JoinColumn(name = "adminGroupId", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private AdminGroup adminGroup;

    public AdminLog createLog() {
        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(this.getId());

        return adminLog;
    }
}
