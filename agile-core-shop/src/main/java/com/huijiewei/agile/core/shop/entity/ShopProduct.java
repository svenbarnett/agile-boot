package com.huijiewei.agile.core.shop.entity;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class ShopProduct extends BaseEntity {
    private String name;

    private String cover;

    private String detail;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "shopCategoryId")
    private ShopCategory shopCategory;

    @ManyToOne
    @JoinColumn(name = "shopBrandId")
    private ShopBrand shopBrand;
}
