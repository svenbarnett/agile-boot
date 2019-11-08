package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ag_admin")
public class Admin extends TimestampEntity {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @PrePersist
    public void passwordEncode() {
        this.password = passwordEncoder.encode(this.password);
    }
}
