package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.constraint.Phone;
import com.huijiewei.agile.base.entity.BaseEntity;
import com.huijiewei.agile.base.entity.DeletedEntity;
import com.huijiewei.agile.base.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_admin")
public class Admin extends BaseEntity implements DeletedEntity, TimestampEntity {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NotBlank
    @Phone
    @Column(unique = true)
    private String phone;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private String avatar;

    @NotNull
    @Column(insertable = false, updatable = false)
    private Integer adminGroupId;

    @ManyToOne
    @JoinColumn(name = "adminGroupId")
    private AdminGroup adminGroup;

    @PrePersist
    public void passwordEncode() {
        this.password = passwordEncoder.encode(this.password);
    }
}
