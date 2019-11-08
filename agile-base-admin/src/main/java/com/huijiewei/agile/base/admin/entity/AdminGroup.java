package com.huijiewei.agile.base.admin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ag_admin_group")
public class AdminGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
