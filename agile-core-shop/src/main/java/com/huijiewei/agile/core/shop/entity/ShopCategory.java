package com.huijiewei.agile.core.shop.entity;

import com.huijiewei.agile.core.entity.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class ShopCategory extends TreeEntity<ShopCategory> {
    @NotBlank
    private String name;

    private String icon;

    private String image;

    private String description;
}
