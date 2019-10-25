package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_admin")
public class Admin extends BaseEntity {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NotBlank
    @Column(unique = true)
    private String phone;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @PrePersist
    public void passwordEncode() {
        this.password = passwordEncoder.encode(this.password);
    }
}
