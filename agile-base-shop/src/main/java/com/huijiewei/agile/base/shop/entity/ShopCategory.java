package com.huijiewei.agile.base.shop.entity;

import com.huijiewei.agile.base.entity.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = ShopCategory.TABLE_NAME)
@DynamicInsert
@DynamicUpdate
public class ShopCategory extends TreeEntity<ShopCategory> {
    public static final String TABLE_NAME = "ag_shop_category";

    @NotBlank
    private String name;

    private String icon;

    private String image;

    private String description;
}
