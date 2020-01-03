package com.huijiewei.agile.core.shop.entity;

import com.huijiewei.agile.core.entity.BaseEntity;
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
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class ShopProduct extends BaseEntity {
    private String name;

    private String cover;

    private String detail;

    private Integer shopCategoryId;

    private Integer shopBrandId;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "shopCategoryId", updatable = false, insertable = false)
    @Setter(AccessLevel.NONE)
    private ShopCategory shopCategory;

    @ManyToOne
    @JoinColumn(name = "shopBrandId", updatable = false, insertable = false)
    @Setter(AccessLevel.NONE)
    private ShopBrand shopBrand;
}
