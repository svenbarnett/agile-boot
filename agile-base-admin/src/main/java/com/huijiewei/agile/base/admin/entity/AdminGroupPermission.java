package com.huijiewei.agile.base.admin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ag_admin_group_permission")
public class AdminGroupPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    private Integer adminGroupId;

    private String actionId;
}
